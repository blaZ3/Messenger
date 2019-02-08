package me.tellvivk.messenger.datasources.sms

import android.content.Context
import android.net.Uri
import io.reactivex.Observable
import me.tellvivk.messenger.app.model.sms.INVALID_SMS
import me.tellvivk.messenger.app.model.sms.SMS
import me.tellvivk.messenger.helpers.logger.LoggerI

class SMSDataSource(private val context: Context,
                    private val logger: LoggerI): SMSDataSourceI {

    override fun getSMSes(page: Int): Observable<SMS> {
        return Observable.create { emitter->

            // public static final String INBOX = "content://sms/inbox";
            // public static final String SENT = "content://sms/sent";
            // public static final String DRAFT = "content://sms/draft";

            val cursor = context.contentResolver
                .query(Uri.parse("content://sms/inbox"),
                    null, null, null, null)

            var isCancelled = false
            cursor?.let {
                if (it.moveToFirst()) { // must check the result to prevent exception
                    do {
//                        var msgData = ""
//                        logger.d("msgData",  "--------------------------------------------")
//                        for (idx in 0 until it.columnCount) {
//                            msgData += " " + it.getColumnName(idx) + ":" + it.getString(idx)
//                            it.getColumnName(idx)?.let { colName->
//                                logger.d("msgData getColumnName",  colName)
//                            }
//
//                            it.getString(idx)?.let { colValue->
//                                logger.d("msgData column value",  colValue)
//                            }
//                        }
//                        logger.d("msgData",  "--------------------------------------------")

                        emitter.onNext(SMS.fromCursorData(cursor))
                    } while (it.moveToNext() && !isCancelled)
                } else {
                    emitter.onNext(INVALID_SMS)
                }
                cursor.close()
            }

            emitter.setCancellable {
                isCancelled = true
            }
        }
    }
}