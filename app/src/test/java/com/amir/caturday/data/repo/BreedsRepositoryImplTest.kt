package com.amir.caturday.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.amir.caturday.TestBreedDao
import com.amir.caturday.TestDataStore
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.breedDtoList
import com.amir.caturday.data.db.BreedDao
import com.amir.caturday.data.db.entity.BreedEntity
import com.amir.caturday.data.remote.BreedsApi
import com.amir.caturday.data.remote.dto.BreedDto
import com.amir.caturday.data.remote.dto.toBreedEntity
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.util.PreferenceKeys
import com.amir.caturday.util.toListWithTimeout
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BreedsRepositoryImplTest {
    private lateinit var dao: BreedDao
    private lateinit var dataStore: DataStore<Preferences>

    @RelaxedMockK
    private lateinit var api: BreedsApi

    @Before
    fun setUp() {
        dao = TestBreedDao()
        dataStore = TestDataStore()
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createRepo(): BreedsRepository = BreedsRepositoryImpl(api, dao, dataStore)

    private suspend fun mockData(
        apiItems: List<BreedDto> = breedDtoList,
        daoItems: List<BreedEntity> = breedDtoList.map { it.toBreedEntity() },
        favoriteItems: List<String> = listOf(),
    ) {
        coEvery {
            api.getBreeds(any(), any())
        } coAnswers {
            apiItems
        }

        dao.insertAll(daoItems)
        dataStore.edit {
            it[PreferenceKeys.FAVORITES] = favoriteItems.joinToString(", ")
        }
    }

    @Test
    fun `when getting breeds from db, it should emit loading state first`() =
        runTest {
            mockData()
            val repo = createRepo()
            val breeds = repo.getBreeds().toListWithTimeout()
            assert(breeds.first() is DataState.Loading)
        }

    @Test
    fun `breeds should be marked as favorite, only when a breed id is in favorites`() =
        runTest {
            mockData(
                apiItems = listOf(),
                daoItems = listOf(breedDto.toBreedEntity().copy(id = "A"), breedDto.toBreedEntity().copy(id = "B")),
                favoriteItems = listOf("A"),
            )
            val repo = createRepo()
            val breeds = repo.getBreeds().toListWithTimeout().last() as DataState.Success

            assertTrue(breeds.data[0].isFavorite)
            assertFalse(breeds.data[1].isFavorite)
        }

    @Test
    fun `when getting item by id, correct item should be returned`() =
        runTest {
            mockData(
                apiItems = listOf(),
                daoItems = listOf(breedDto.toBreedEntity().copy(id = "A"), breedDto.toBreedEntity().copy(id = "B")),
            )
            val repo = createRepo()
            val breed = repo.getBreedById("A").last() as DataState.Success
            assertEquals("A", breed.data.id)
        }

    @Test
    fun `when getting item by id, and it is not found, failure should be returned`() =
        runTest {
            mockData(
                apiItems = listOf(),
                daoItems = listOf(breedDto.toBreedEntity().copy(id = "A"), breedDto.toBreedEntity().copy(id = "B")),
            )
            val repo = createRepo()
            val breed = repo.getBreedById("C").last()
            assert(breed is DataState.Failure)
        }

    @Test
    fun `when toggling an item to favorite, it should be saved in favorites`() =
        runTest {
            mockData(
                daoItems = listOf(breedDto.toBreedEntity().copy("A")),
                favoriteItems = listOf(),
            )
            val repo = createRepo()
            val breeds = repo.getBreeds().toListWithTimeout().last() as DataState.Success

            assertFalse(breeds.data.first().isFavorite)
            repo.toggleFavorite("A", true)
            val breeds2 = repo.getBreeds().toListWithTimeout().last() as DataState.Success
            assertTrue(breeds2.data.first().isFavorite)
        }

    @Test
    fun `when toggling an item from favorite, it should be removed from favorites`() =
        runTest {
            mockData(
                daoItems = listOf(breedDto.toBreedEntity().copy("A")),
                favoriteItems = listOf("A"),
            )
            val repo = createRepo()
            val breeds = repo.getBreeds().toListWithTimeout().last() as DataState.Success

            assertTrue(breeds.data.first().isFavorite)
            repo.toggleFavorite("A", false)
            val breeds2 = repo.getBreeds().toListWithTimeout().last() as DataState.Success
            assertFalse(breeds2.data.first().isFavorite)
        }
}
