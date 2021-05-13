package com.example.scango

import android.database.DataSetObserver
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverviewFragment : Fragment() {

    private lateinit var checkoutButton: Button
    private lateinit var scanButton: Button
    private lateinit var productListView: ListView
    private lateinit var totalPriceTextView: TextView

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
        productListView = view.findViewById(R.id.listviewProducts)
        totalPriceTextView = view.findViewById(R.id.totalPrice)
        val format = DecimalFormat("0.00")
        totalPriceTextView.text = format.format(GroceriesManager.getTotalPrice())
        setNavigation()

        val productListAdapter =
            context?.let { ProductsListAdapter(it, R.layout.list_item_product, activity!!) }
        productListView.adapter = productListAdapter
        productListAdapter?.notifyDataSetChanged()
        productListAdapter?.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                totalPriceTextView.text = format.format(GroceriesManager.getTotalPrice())
            }
        })

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
}