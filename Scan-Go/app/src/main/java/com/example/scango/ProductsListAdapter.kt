package com.example.scango

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductsListAdapter(context: Context, private val resource: Int, private val activity: FragmentActivity) : BaseAdapter() {

    private var inflater: LayoutInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    private lateinit var productAdd : Button
    private lateinit var productRemove : Button

    override fun getCount(): Int {
        return GroceriesManager.getProducts().size
    }

    override fun getItem(position: Int): Product {
        return GroceriesManager.getProducts()[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
        } else {
            view = convertView
        }

        val productName = view?.findViewById<TextView>(R.id.listProductName)
        val productPrice = view?.findViewById<TextView>(R.id.listProductPrice)
        val productAmount = view?.findViewById<TextView>(R.id.listProductAmount)
        productAdd = view?.findViewById(R.id.listProductAdd)!!
        productRemove = view.findViewById(R.id.listProductRemove)!!

        productName?.text = getItem(position).getProductName()
        val format = DecimalFormat("0.00")
        productPrice?.text = format.format(getItem(position).getTotalPrice())
        productAmount?.text = getItem(position).getAmount().toString()

        setButtonActions(getItem(position))

        return view
    }

    private fun setButtonActions(product : Product) {
        productAdd.setOnClickListener(View.OnClickListener {
            product.increaseAmount()
            GroceriesManager.setTotalPrice()
            notifyDataSetChanged()
        })
        productRemove.setOnClickListener(View.OnClickListener {
            if(product.getAmount() == 1)
            {
                setRemoveProductDialog(product)
            } else {
                product.decreaseAmount()
                GroceriesManager.setTotalPrice()
                notifyDataSetChanged()
            }
        })
    }

    private fun setRemoveProductDialog(product: Product) {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Removing a product")
        alertDialogBuilder.setMessage("Are you sure you want to delete " + product.getProductName() + "?")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            GroceriesManager.getProducts().remove(product)
            GroceriesManager.setTotalPrice()
            notifyDataSetChanged()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

}