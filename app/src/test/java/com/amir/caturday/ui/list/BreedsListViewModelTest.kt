package com.amir.caturday.ui.list

import com.amir.caturday.MainDispatcherRule
import com.amir.caturday.data.breedDto
import com.amir.caturday.data.breedDtoList
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.domain.usecase.breed.GetBreedsUseCase
import com.amir.caturday.domain.usecase.breed.InvalidateCacheUseCase
import com.amir.caturday.domain.usecase.breed.PaginateBreedsUseCase
import com.amir.caturday.domain.usecase.breed.ToggleFavoriteUseCase
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

class BreedsListViewModelTest {
    @RelaxedMockK
    private lateinit var getBreedsUseCase: GetBreedsUseCase

    @RelaxedMockK
    private lateinit var paginateBreedsUseCase: PaginateBreedsUseCase

    @RelaxedMockK
    private lateinit var invalidateCacheUseCase: InvalidateCacheUseCase

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

    private fun mockData(
        getBreedsResponse: List<Pair<Long, DataState<List<Breed>>>> = listOf(),
        paginateBreedsResponse: List<Pair<Long, DataState<Unit>>> = listOf(),
    ) {
        coEvery {
            getBreedsUseCase()
        } coAnswers {
            flow {
                getBreedsResponse.forEach {
                    delay(it.first)
                    emit(it.second)
                }
            }
        }
        coEvery {
            paginateBreedsUseCase(any())
        } coAnswers {
            flow {
                paginateBreedsResponse.forEach {
                    delay(it.first)
                    emit(it.second)
                }
            }
        }
    }

    private fun createViewModel(): BreedsListViewModel =
        BreedsListViewModel(
            getBreedsUseCase = getBreedsUseCase,
            paginateBreedsUseCase = paginateBreedsUseCase,
            invalidateCacheUseCase = invalidateCacheUseCase,
            toggleFavoriteUseCase = toggleFavoriteUseCase,
        )

    @Test
    fun `when viewmodel is created, breeds should be observed`() =
        runTest {
            createViewModel()
            coVerify(exactly = 1) {
                getBreedsUseCase()
            }
        }

    @Test
    fun `when getBreedsUseCase emits loading, state should be updated correctly`() =
        runTest {
            mockData(getBreedsResponse = listOf(0L to DataState.Loading))
            val vm = createViewModel()
            assertTrue(vm.uiState.value.isLoading)
        }

    @Test
    fun `when getBreedsUseCase emits successful data, breeds should be updated in state`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(listOf(breedDto.toBreed())),
                        2000L to DataState.Success(listOf(breedDto.toBreed(), breedDto.toBreed())),
                    ),
            )
            val vm = createViewModel()
            delay(1500)
            assertEquals(1, vm.uiState.value.breeds.size)
            delay(2500)
            assertEquals(2, vm.uiState.value.breeds.size)
        }

    @Test
    fun `when getBreedsUseCase emits successful data, state should not be loading`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(listOf(breedDto.toBreed())),
                    ),
            )
            val vm = createViewModel()
            assertTrue(vm.uiState.value.isLoading)
            delay(1500)
            assertFalse(vm.uiState.value.isLoading)
        }

    @Test
    fun `when getBreedsUseCase emits successful data, hasMore should update correctly`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(listOf(breedDto.toBreed())),
                        2000L to DataState.Success(listOf(breedDto.toBreed())),
                    ),
            )
            val vm = createViewModel()
            assertTrue(vm.uiState.value.hasMore)
            delay(1500)
            assertTrue(vm.uiState.value.hasMore)
            delay(2500)
            assertFalse(vm.uiState.value.hasMore)
        }

    @Test
    fun `when getBreedsUseCase emits failure data, error message should update the state`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            assertEquals("A", vm.uiState.value.errorMessage)
        }

    @Test
    fun `when getBreedsUseCase emits failure data while paginating, show error should be true for a limited time`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(listOf(breedDto.toBreed())),
                        2000L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            delay(3500)
            assertTrue(vm.uiState.value.showError)
            delay(3000)
            assertFalse(vm.uiState.value.showError)
        }

    @Test
    fun `when getBreedsUseCase emits failure at start, show error should remain true until retry is called`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            delay(10_000)
            assertTrue(vm.uiState.value.showError)
            vm.onRetryClick()
            assertFalse(vm.uiState.value.showError)
        }

    @Test
    fun `when paginate is called while loading, nothing should happen`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(listOf(breedDto.toBreed())),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            coVerify(exactly = 0) {
                paginateBreedsUseCase(any())
            }
        }

    @Test
    fun `when paginate is called, page should be calculated correctly based on breeds data`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Failure(0, "A"),
                        1000L to DataState.Success(breedDtoList.map { it.toBreed() }),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            coVerify(exactly = 1) {
                paginateBreedsUseCase(0)
            }
            delay(1500)
            vm.onPaginate()
            coVerify(exactly = 1) {
                paginateBreedsUseCase(1)
            }
        }

    @Test
    fun `when pagination fails, error message should update the state`() =
        runTest {
            mockData(
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            assertEquals("A", vm.uiState.value.errorMessage)
        }

    @Test
    fun `when pagination fails, state should not be loading`() =
        runTest {
            mockData(
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            assertFalse(vm.uiState.value.isLoading)
        }

    @Test
    fun `when pagination fails while paginating, show error should be true for a limited time`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to DataState.Success(listOf(breedDto.toBreed())),
                    ),
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            assertTrue(vm.uiState.value.showError)
            delay(3000)
            assertFalse(vm.uiState.value.showError)
        }

    @Test
    fun `when pagination fails at start, show error should remain true until retry is called`() =
        runTest {
            mockData(
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Failure(1, "A"),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            delay(10_000)
            assertTrue(vm.uiState.value.showError)
            mockData(
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                    ),
            )
            vm.onRetryClick()
            assertFalse(vm.uiState.value.showError)
        }

    @Test
    fun `when paginate emits loading, state should be updated to loading`() =
        runTest {
            mockData(
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            assertTrue(vm.uiState.value.isLoading)
        }

    @Test
    fun `when pagination is successful, state should not be loading`() =
        runTest {
            mockData(
                paginateBreedsResponse =
                    listOf(
                        0L to DataState.Loading,
                        1000L to DataState.Success(Unit),
                    ),
            )
            val vm = createViewModel()
            vm.onPaginate()
            assertTrue(vm.uiState.value.isLoading)
            delay(1500)
            assertFalse(vm.uiState.value.isLoading)
        }

    @Test
    fun `when search query is changed, state should update to loading`() =
        runTest {
            mockData()
            val vm = createViewModel()
            assertFalse(vm.uiState.value.isLoading)
            vm.onSearchQueryChanged("A")
            assertTrue(vm.uiState.value.isLoading)
        }

    @Test
    fun `when search query is blank, state should not be searching`() =
        runTest {
            mockData()
            val vm = createViewModel()
            vm.onSearchQueryChanged("A")
            delay(1000)
            assertTrue(vm.uiState.value.isSearching)
            vm.onSearchQueryChanged("  ")
            assertFalse(vm.uiState.value.isSearching)
        }

    @Test
    fun `when search query is changed, search data should update after a delay`() =
        runTest {
            mockData(
                getBreedsResponse =
                    listOf(
                        0L to
                            DataState.Success(
                                listOf(
                                    breedDto.copy(name = "A").toBreed(),
                                    breedDto.copy(name = "B").toBreed(),
                                ),
                            ),
                    ),
            )
            val vm = createViewModel()
            vm.onSearchQueryChanged("A")
            assertEquals(0, vm.uiState.value.searchingBreeds.size)
            delay(1000)
            assertEquals(1, vm.uiState.value.searchingBreeds.size)
        }
}
