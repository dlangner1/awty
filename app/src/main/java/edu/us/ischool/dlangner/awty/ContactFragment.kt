package edu.us.ischool.dlangner.awty

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MESSAGE = "message"
private const val PHONE_NUMBER = "phone_number"
private const val MINUTES = "minutes"

class ContactFragment : Fragment() {

    private var isSendingMessage: Boolean = false

    private var messageEditText: EditText? = null
    private var phoneEditText: EditText? = null
    private var minutesEditText: EditText? = null
    private var startStopButton: Button? = null

    private var listener: OnFragmentInteractionListener? = null

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        fun onStartStopPressed(shouldStart: Boolean, message: String, phoneNumber: String, duration: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_contact, container, false)

        messageEditText = rootView.findViewById(R.id.message)
        phoneEditText = rootView.findViewById(R.id.phone_number)
        minutesEditText = rootView.findViewById(R.id.minutes)
        startStopButton = rootView.findViewById(R.id.start_stop_button)

        startStopButton?.setBackgroundColor(resources.getColor(R.color.colorPrimary))

        startStopButton?.setTextColor(Color.WHITE)
        startStopButton?.setOnClickListener {

            if (!messageEditText?.text.isNullOrBlank() and
                !phoneEditText?.text.isNullOrBlank() and
                !minutesEditText?.text.isNullOrBlank()) {

                isSendingMessage = !isSendingMessage

                startStopButton?.text = if (isSendingMessage) "Stop" else "Start"
                startStopButton?.setBackgroundColor(if (isSendingMessage) resources.getColor(R.color.colorAccent)
                else resources.getColor(R.color.colorPrimary))


                listener?.onStartStopPressed(
                    isSendingMessage,
                    messageEditText?.text.toString(),
                    phoneEditText?.text.toString(),
                    minutesEditText?.text.toString().toInt()
                )
            }
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ContactFragment.
         */
        @JvmStatic
        fun newInstance() =
            ContactFragment().apply {
            }
    }
}
