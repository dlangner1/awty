package edu.us.ischool.dlangner.awty

import android.app.Service
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import java.util.*
import kotlin.concurrent.timerTask

private const val CONTACT_FRAGMENT_TAG = "CONTACT_FRAGMENT_CONTACT"

class MainActivity : AppCompatActivity(),
    ContactFragment.OnFragmentInteractionListener {

    lateinit var toast: Toast
    var timeToResend: Long = 0
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactFragment = ContactFragment.newInstance()
        supportFragmentManager.beginTransaction().run {
            add(R.id.main_container, contactFragment, CONTACT_FRAGMENT_TAG)
            commit()
        }
    }

    override fun onStartStopPressed(shouldStart: Boolean, message: String, phoneNumber: String, duration: Int) {
        if (shouldStart) {
            // create toast message
            val formattedNumber = PhoneNumberUtils.formatNumber(phoneNumber, "1", "US")
            val formattedMessage = formattedNumber.plus(": ").plus(message)
            toast = Toast.makeText(this, formattedMessage, Toast.LENGTH_LONG)
            timeToResend = (duration * 60000).toLong() // converts minutes to milliseconds

            val handler = Handler()

            timer.scheduleAtFixedRate(timerTask {
                run {
                    handler.post {
                        run {
                            toast.show()
                        }
                    }
                }
            }, 0, timeToResend)
        } else {
            // stop service
            timer.cancel()
        }
    }
}
