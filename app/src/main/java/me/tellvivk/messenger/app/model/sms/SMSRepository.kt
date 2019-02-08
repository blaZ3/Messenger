package me.tellvivk.messenger.app.model.sms

import io.reactivex.Single
import me.tellvivk.messenger.datasources.sms.SMSDataSourceI
import me.tellvivk.messenger.helpers.valueFormatter.ValueFormatterI

class SMSRepository(private val smsDataSource: SMSDataSourceI,
                    private val valueFormatter: ValueFormatterI) : SMSRepositoryI {

    override fun getSMS(page: Int): Single<List<SMS>> {
        return smsDataSource.getSMSes(page).takeUntil { sms->
            hrs24ago.minus(sms.date!!) > 0
        }.toList()
    }

}