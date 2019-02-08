package me.tellvivk.messenger.app.model.sms

import android.database.Cursor
import android.telephony.SmsMessage
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull

data class SMS(
    val id: Int? = 0,
    val threadId: Int? = 0,
    val address: String? = "",
    val body: String? = "",
    val date: Long? = 0,
    val subject: String? = "",
    val seen: Int? = 0,
    val read: Int? = 0,
    val sent: Int? = 0
){
    companion object {
        fun fromCursorData(cursor: Cursor): SMS{
            return SMS(
                id = cursor.getIntOrNull(cursor.getColumnIndex(COLUMN_ID)),
                threadId = cursor.getIntOrNull(cursor.getColumnIndex(COLUMN_THREAD_ID)),
                address = cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_ADDRESS)),
                body = cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_BODY)),
                date = cursor.getLongOrNull(cursor.getColumnIndex(COLUMN_DATE)),
                subject = cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_SUBJECT)),
                seen = cursor.getIntOrNull(cursor.getColumnIndex(COLUMN_SEEN)),
                read = cursor.getIntOrNull(cursor.getColumnIndex(COLUMN_READ)),
                sent = cursor.getIntOrNull(cursor.getColumnIndex(COLUMN_SENT))
            )
        }

        fun fromSmsMessage(msg: SmsMessage): SMS{
            return SMS(
                id = msg.indexOnIcc,
                address = msg.originatingAddress,
                body = msg.messageBody,
                subject = msg.displayMessageBody
            )
        }
    }
}

val INVALID_SMS = SMS(id = -1)
val hrInMillis = 60 * 60 * 1000
val hrs24ago = System.currentTimeMillis() - (24 * hrInMillis)

const val COLUMN_ID = "_id"
const val COLUMN_THREAD_ID = "thread_id"
const val COLUMN_ADDRESS = "address"
const val COLUMN_DATE = "date"
const val COLUMN_SUBJECT = "subject"
const val COLUMN_BODY = "body"
const val COLUMN_SENT = "date_sent"
const val COLUMN_READ = "read"
const val COLUMN_SEEN = "seen"