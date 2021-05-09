package com.example.scango

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverviewFragment : Fragment() {

    private lateinit var checkoutButton: Button
    private lateinit var scanButton: Button
    private val databaseManager = DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        checkoutButton = view.findViewById(R.id.checkoutButton)
        scanButton = view.findViewById(R.id.scanButton)
        setNavigation()
        databaseManager.setProduct()

        return view;
    }

    private fun setNavigation() {
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        checkoutButton.setOnClickListener {
            if (transaction != null) {
                transaction.replace(R.id.fragmentContainer, CheckoutFragment()).remove(this)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        scanButton.setOnClickListener {
            if (transaction != null) {
                transaction.replace(R.id.fragmentContainer, ScanFragment()).remove(this)
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
         * @return A new instance of fragment OverviewFragment.
         */
        @JvmStatic
        fun newInstance() =
            OverviewFragment()
    }
    */
}