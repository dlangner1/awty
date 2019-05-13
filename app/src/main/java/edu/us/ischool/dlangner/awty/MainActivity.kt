package edu.us.ischool.dlangner.awty

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

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

    override fun onStartStopPressed() {

    }
}
