package me.tellvivk.messenger.app.model.sms

import io.reactivex.Single

interface SMSRepositoryI {

    fun getSMS(page: Int): Single<List<SMS>>

}