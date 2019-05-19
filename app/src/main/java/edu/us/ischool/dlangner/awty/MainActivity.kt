package edu.us.ischool.dlangner.awty

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberUtils
import java.util.*
import kotlin.concurrent.timerTask
import android.Manifest.permission_group.SMS
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.telephony.SmsManager


private const val CONTACT_FRAGMENT_TAG = "CONTACT_FRAGMENT_CONTACT"

class MainActivity : AppCompatActivity(),
    ContactFragment.OnFragmentInteractionListener {

    var timeToResend: Long = 0
    private var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 1)
        }


        val contactFragment = ContactFragment.newInstance()
        supportFragmentManager.beginTransaction().run {
            add(R.id.main_container, contactFragment, CONTACT_FRAGMENT_TAG)
            commit()
        }
    }

    override fun onStartStopPressed(shouldStart: Boolean, message: String, phoneNumber: String, duration: Int) {
        if (shouldStart) {

            if (timer == null) {
                timer = Timer()
            }

            val formattedNumber = PhoneNumberUtils.formatNumber(phoneNumber, "1", "US")
            timeToResend = (duration * 60000).toLong() // converts minutes to milliseconds

            val handler = Handler()
            timer.scheduleAtFixedRate(timerTask {
                run {
                    handler.post {
                        run {
                            sendSMS(formattedNumber, message)
                        }
                    }
                }
            }, 0, timeToResend)
        } else {
            // stop service
            timer.cancel()
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val pi = PendingIntent.getActivity(
            this, 0,
            Intent(this, SMS::class.java), 0
        )
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, pi, null)
    }
}
