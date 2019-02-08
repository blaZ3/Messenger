package me.tellvivk.messenger.helpers.valueFormatter

import me.tellvivk.messenger.app.model.sms.SMS

interface ValueFormatterI {

    fun getDateTimeStringFromMillis(millis: Long): String
    fun getTimeForDisplay(millis: Long?): String
    fun getDpString(address: String): String
    fun getSmsSubject(sms: SMS): String

}