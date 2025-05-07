package com.amir.caturday.ui.list

import androidx.lifecycle.SavedStateHandle
import com.amir.caturday.MainDispatcherRule
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.domain.usecase.breed.GetBreedByIdUseCase
import com.amir.caturday.domain.usecase.breed.ToggleFavoriteUseCase
import com.amir.caturday.ui.details.BreedsDetailsViewModel
import com.amir.caturday.ui.main.Screen
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BreedsDetailsViewModelTest {
    @RelaxedMockK
    private lateinit var getBreedByIdUseCase: GetBreedByIdUseCase

    @RelaxedMockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun mockData(getBreedByIdResponse: List<Pair<Long, DataState<Breed>>> = listOf()) {
        coEvery {
            getBreedByIdUseCase(any())
        } coAnswers {
            flow {
                getBreedByIdResponse.forEach {
                    delay(it.first)
                    emit(it.second)
                }
            }
        }
    }

    private fun createViewModel(): BreedsDetailsViewModel =
        BreedsDetailsViewModel(
            getBreedByIdUseCase = getBreedByIdUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase,
            savedStateHandle = SavedStateHandle().apply { set(Screen.ARG_ID, "A") },
        )

    @Test
    fun `when viewmodel is created, breed should be observed`() =
        runTest {
            createViewModel()
            coVerify(exactly = 1) {
                getBreedByIdUseCase(any())
            }
        }

    @Test
    fun `when getBreedsUseCase emits loading, state should be updated correctly`() =
        runTest {
            mockData(getBreedByIdResponse = listOf(0L to DataState.Loading))
            val vm = createViewModel()
            assertTrue(vm.uiState.value.isLoading)
        }

    @Test
    fun `when getBreedByIdUseCase emits successful data, breed should be updated in state`() =
        runTest {
            mockData(
                getBreedByIdResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(breedDto.toBreed().copy(isFavorite = true)),
                        2000L to DataState.Success(breedDto.toBreed().copy(isFavorite = false)),
                    ),
            )
            val vm = createViewModel()
            delay(1500)
            assertEquals(
                true,
                vm.uiState.value.breed
                    ?.isFavorite,
            )
            delay(2500)
            assertEquals(
                false,
                vm.uiState.value.breed
                    ?.isFavorite,
            )
        }

    @Test
    fun `when getBreedsUseCase emits successful data, state should not be loading`() =
        runTest {
            mockData(
                getBreedByIdResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(breedDto.toBreed()),
                    ),
            )
            val vm = createViewModel()
            assertTrue(vm.uiState.value.isLoading)
            delay(1500)
            assertFalse(vm.uiState.value.isLoading)
        }

    @Test
    fun `when getBreedsUseCase emits failure data, error message should update the state`() =
        runTest {
            mockData(
                getBreedByIdResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            assertEquals("A", vm.uiState.value.errorMessage)
        }
}
