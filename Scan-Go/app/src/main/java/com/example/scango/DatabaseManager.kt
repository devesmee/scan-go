package com.example.scango

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

object DatabaseManager {
    private var databaseReference: DatabaseReference = Firebase.database.reference

    fun addProduct(productId: String, activity: FragmentActivity) {
        databaseReference.child("Products").child(productId).get().addOnCompleteListener {
            if (it.result.value == null) {
                setFailedDialog(activity)
            } else {
                val productName = it.result.child("name").value.toString()
                val productPrice = it.result.child("price").value as Double
                val product = Product(productName, productPrice, 1, productPrice)
                GroceriesManager.addToProducts(product)
                setSuccessDialog(activity)
            }
        }
    }

    private fun setSuccessDialog(activity: FragmentActivity) {
        val successDialog = Dialog(activity)
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successDialog.setCancelable(false)
        successDialog.setContentView(R.layout.dialog_layout)
        val title = successDialog.findViewById<TextView>(R.id.dialog_title)
        title.text = activity.getString(R.string.product_added_title)
        val body = successDialog.findViewById<TextView>(R.id.dialog_text)
        body.text = activity.getString(R.string.product_added_text)
        val confirmButton = successDialog.findViewById<Button>(R.id.dialog_ok_button)
        confirmButton.setOnClickListener {
            successDialog.dismiss()
            val manager = activity.supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, OverviewFragment())
            transaction.commit()
        }
        val yesButton = successDialog.findViewById<Button>(R.id.dialog_yes_button)
        val noButton = successDialog.findViewById<Button>(R.id.dialog_no_button)
        yesButton.visibility = View.INVISIBLE
        noButton.visibility = View.INVISIBLE
        successDialog.show()
    }

    private fun setFailedDialog(activity: FragmentActivity) {
        val failedDialog = Dialog(activity)
        failedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        failedDialog.setCancelable(false)
        failedDialog.setContentView(R.layout.dialog_layout)
        val title = failedDialog.findViewById<TextView>(R.id.dialog_title)
        title.text = activity.getString(R.string.product_not_found_title)
        val body = failedDialog.findViewById<TextView>(R.id.dialog_text)
        body.text = activity.getString(R.string.product_not_found_text)
        val confirmButton = failedDialog.findViewById<Button>(R.id.dialog_ok_button)
        confirmButton.setOnClickListener {
            failedDialog.dismiss()
            val manager = activity.supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, ScanFragment())
            transaction.commit()
        }
        val yesButton = failedDialog.findViewById<Button>(R.id.dialog_yes_button)
        val noButton = failedDialog.findViewById<Button>(R.id.dialog_no_button)
        yesButton.visibility = View.INVISIBLE
        noButton.visibility = View.INVISIBLE
        failedDialog.show()
    }
}