package edu.us.ischool.dlangner.awty

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.Toast

private const val CONTACT_FRAGMENT_TAG = "CONTACT_FRAGMENT_CONTACT"

class MainActivity : AppCompatActivity(),
    ContactFragment.OnFragmentInteractionListener {

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
            val toast = Toast.makeText(this, formattedMessage, Toast.LENGTH_LONG)

            // start running on timer for background service

        } else {
            // stop service
        }
    }
}
