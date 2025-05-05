package com.amir.caturday.domain.usecase

import com.amir.caturday.data.repo.BreedsRepository
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.domain.usecase.breed.SearchBreedsUseCase
import com.amir.caturday.util.toListWithTimeout
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchBreedsUseCaseTest {
    @RelaxedMockK
    private lateinit var repo: BreedsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    fun createUseCase(): SearchBreedsUseCase = SearchBreedsUseCase(repo)

    fun mockData() {
        coEvery {
            repo.searchBreeds(any())
        } coAnswers {
            flow {
                emit(DataState.Success(listOf()))
            }
        }
    }

    @Test
    fun `when input is empty or blank, it should return failure`() =
        runTest {
            val uc = createUseCase()
            val result = uc("").toListWithTimeout().last()
            assert(result is DataState.Failure)
            val result2 = uc("  ").toListWithTimeout().last()
            assert(result2 is DataState.Failure)
        }

    @Test
    fun `when input is empty or blank, it should not call repo`() =
        runTest {
            val uc = createUseCase()
            uc("").toListWithTimeout()
            coVerify(exactly = 0) {
                repo.searchBreeds(any())
            }
        }

    @Test
    fun `when calling repo, it should trim the query`() =
        runTest {
            val uc = createUseCase()
            uc(" A ").toListWithTimeout()
            coVerify(exactly = 1) {
                repo.searchBreeds("A")
            }
        }
}
