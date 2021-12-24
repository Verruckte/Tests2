package com.geekbrains.tests

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.repository.FakeGitHubRepository
import com.geekbrains.tests.stubs.ScheduleProviderStub
import com.geekbrains.tests.view.search.ScreenState
import com.geekbrains.tests.view.search.SearchViewModel
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SearchViewModelLiveTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    @Mock
    private lateinit var repository: FakeGitHubRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchViewModel = SearchViewModel(repository, ScheduleProviderStub())
    }

    @Test
    fun liveData_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            //Получаем LiveData
            val liveData = searchViewModel.subscribeToLiveData()

            //При вызове Репозитория возвращаем шаблонные данные
            `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
                Observable.just(
                    SearchResponse(
                        1,
                        listOf()
                    )
                )
            )

            try {
                //Подписываемся на LiveData без учета жизненного цикла
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                //Убеждаемся, что Репозиторий вернул данные и LiveData передала их Наблюдателям
                Assert.assertNotNull(liveData.value)
            } finally {
                //Тест закончен, снимаем Наблюдателя
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun liveData_TestReturnValueIsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()
            val error = Throwable(ERROR_TEXT)

            //При вызове Репозитория возвращаем ошибку
            `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
                Observable.error(error)
            )

            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                //Убеждаемся, что Репозиторий вернул ошибку и LiveData возвращает ошибку
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, error.message)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestException() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()

            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)

                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, ERROR_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }


    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }

}