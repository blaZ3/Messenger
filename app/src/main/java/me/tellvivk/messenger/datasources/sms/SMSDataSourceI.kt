package me.tellvivk.messenger.datasources.sms

import io.reactivex.Observable
import me.tellvivk.messenger.app.model.sms.SMS

interface SMSDataSourceI {

    fun getSMSes(page: Int): Observable<SMS>

}