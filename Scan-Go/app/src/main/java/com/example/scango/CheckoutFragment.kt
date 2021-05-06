package com.example.scango

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {

    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)
        cancelButton = view.findViewById(R.id.cancelButton)
        setNavigation()

        return view;
    }

    private fun setNavigation() {
        cancelButton.setOnClickListener {
            val manager = activity?.supportFragmentManager
            val transaction = manager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.fragmentContainer, OverviewFragment())
                transaction.commit()
            }
        }
    }

    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CheckoutFragment.
         */
        @JvmStatic
        fun newInstance() =
            CheckoutFragment()
    }
    */
}