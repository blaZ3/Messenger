package me.tellvivk.messenger.app.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import me.tellvivk.messenger.CHANNEL_ID
import me.tellvivk.messenger.R
import me.tellvivk.messenger.app.model.sms.SMS
import me.tellvivk.messenger.app.screens.home.HomeScreen


class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val smses = arrayListOf<SMS>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Telephony.Sms.Intents.getMessagesFromIntent(intent).map {
                    smses.add(SMS.fromSmsMessage(it))
                }
            } else {
                val smsBundle = intent.extras
                if (smsBundle != null) {
                    val pdus = smsBundle.get("pdus") as Array<Any>
                    if (pdus == null) {
                        Log.e("onReceive", "SmsBundle had no pdus key")
                        return
                    }
                    val messages = arrayOfNulls<SmsMessage>(pdus.size)
                    messages.indices.forEach {
                        smses.add(
                            SMS.fromSmsMessage(SmsMessage.createFromPdu(pdus[it] as ByteArray))
                        )
                    }
                }
            }

            smses.forEach {
                showNotification(context, it)
            }
        }
    }


    private fun showNotification(context: Context, sms: SMS){
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, HomeScreen::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context,
            0, intent, 0)

        val notificationBuilder = NotificationCompat.Builder(context,
            CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(sms.address)
            .setContentText(sms.body)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(sms.body))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        //show only latest message from an address
        sms.address.hashCode().let { smsId->
            with(NotificationManagerCompat.from(context)) {
                notify(smsId, notificationBuilder.build())
            }
        }


    }



}