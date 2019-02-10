package me.tellvivk.messenger.helpers.valueFormatter

import android.content.Context
import android.graphics.Color
import me.tellvivk.messenger.R
import me.tellvivk.messenger.app.model.sms.SMS
import java.text.SimpleDateFormat
import java.util.*


class ValueFormatter(private val context: Context): ValueFormatterI {

    override fun getDateTimeStringFromMillis(millis: Long): String {
        return getDateTimeAsString(millis, "dd/MM/yyyy hh:mm:ss.SSS")
    }

    override fun getTimeForDisplay(millis: Long?): String {
        millis?.let {
            return getDateTimeAsString(millis, "hh:mm")
        }
        return ""
    }

    private fun getDateTimeAsString(millis: Long, format: String): String{
        val formatter = SimpleDateFormat(format)
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

    override fun getRandomColor(): Int {
        val allColors = context.resources.getStringArray(R.array.colorsArray)
        return  Color.parseColor(allColors.random())
    }

    override fun getUnSeenColor(): Int {
        return context.resources.getColor(R.color.primary_dark)
    }


}