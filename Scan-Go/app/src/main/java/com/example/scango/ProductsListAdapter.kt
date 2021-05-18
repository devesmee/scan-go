package com.example.scango

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import org.w3c.dom.Text
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductsListAdapter(private val context: Context, private val resource: Int, private val activity: FragmentActivity) : BaseAdapter() {

    private var inflater: LayoutInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    private lateinit var productAdd : ImageButton
    private lateinit var productRemove : ImageButton

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
        productPrice?.text = context.getString(R.string.item_price, format.format(getItem(position).getTotalPrice()))
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
        val removeProductDialog = Dialog(context)
        removeProductDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        removeProductDialog.setCancelable(false)
        removeProductDialog.setContentView(R.layout.dialog_layout)
        val title = removeProductDialog.findViewById<TextView>(R.id.dialog_title)
        title.text = context.getString(R.string.remove_product_title)
        val body = removeProductDialog.findViewById<TextView>(R.id.dialog_text)
        body.text = context.getString(R.string.remove_product_text, product.getProductName())
        val okButton = removeProductDialog.findViewById<Button>(R.id.dialog_ok_button)
        okButton.visibility = View.INVISIBLE
        val yesButton = removeProductDialog.findViewById<Button>(R.id.dialog_yes_button)
        yesButton.setOnClickListener {
            GroceriesManager.getProducts().remove(product)
            GroceriesManager.setTotalPrice()
            notifyDataSetChanged()
            removeProductDialog.dismiss()
        }
        val noButton = removeProductDialog.findViewById<Button>(R.id.dialog_no_button)
        noButton.setOnClickListener{
            removeProductDialog.dismiss()
        }
        removeProductDialog.show()
    }

}