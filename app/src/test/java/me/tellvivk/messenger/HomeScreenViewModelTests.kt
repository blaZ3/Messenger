package me.tellvivk.messenger

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import me.tellvivk.messenger.app.model.sms.SMSRepositoryI
import me.tellvivk.messenger.app.screens.home.HomeScreenViewModel
import me.tellvivk.messenger.app.screens.home.InitHomeScreen
import me.tellvivk.messenger.helpers.TestHelper
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class HomeScreenViewModelTests {

    private lateinit var smsRepo: SMSRepositoryI
    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup(){

    }


    @Test
    fun `when HomeScreenViewModel init InitHomeScreen is called`(){
        smsRepo = mock()
        whenever(smsRepo.getSMS(1)).thenReturn(Single
            .just(TestHelper.getDummySmes())
            .delay(TestHelper.DUMMY_DELAY, TimeUnit.MILLISECONDS)
        )
        viewModel = HomeScreenViewModel(smsRepo = smsRepo)

        val stateModelTestObservable =
            viewModel.getViewModelObservable().test()

        var viewEventTestObservable =
            viewModel.getViewEventObservable().test()



        Thread.sleep(100)
        stateModelTestObservable
            .assertNoErrors()

        viewEventTestObservable
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(InitHomeScreen)


    }

}