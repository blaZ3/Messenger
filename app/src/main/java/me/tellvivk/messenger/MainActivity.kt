package me.tellvivk.messenger

import android.os.Bundle
import me.tellvivk.messenger.app.base.BaseActivity
import me.tellvivk.messenger.app.base.BaseView
import me.tellvivk.messenger.app.base.StateModel
import me.tellvivk.messenger.app.base.ViewEvent
import me.tellvivk.messenger.app.screens.home.HomeScreen

class MainActivity : BaseActivity() {
    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getParentView(): BaseView? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateView(stateModel: StateModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleEvent(event: ViewEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //todo
//    lateinit var smsRepository: SMSRepositoryI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        smsRepository = get()
//        btnGetSmses.setOnClickListener {
//            smsRepository.getSMS(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess {
//
//                }.subscribe()
//        }


        HomeScreen.start(this)

    }



}
