package me.tellvivk.messenger.helpers.valueFormatter

import me.tellvivk.messenger.app.model.sms.SMS
import java.text.SimpleDateFormat
import java.util.*


class ValueFormatter: ValueFormatterI {

    override fun getDateTimeStringFromMillis(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return formatter.format(calendar.time)
    }


    override fun getDpString(address: String): String {
        return "${address.toCharArray()[0]}${address.toCharArray()[1]}"
    }

    override fun getSmsSubject(sms: SMS): String {
        if(!sms.subject.isNullOrEmpty()){
            return sms.subject
        }
        return "--"
    }
}