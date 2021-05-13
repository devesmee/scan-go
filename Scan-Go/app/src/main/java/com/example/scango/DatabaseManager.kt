package com.example.scango

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
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
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Product was added!")
        alertDialogBuilder.setNeutralButton("OK") { dialog, which ->
            val manager = activity.supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, OverviewFragment())
            transaction.commit()
        }
        alertDialogBuilder.show()
    }

    private fun setFailedDialog(activity: FragmentActivity) {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Product was not found!")
        alertDialogBuilder.setMessage("Please ask the cashier to scan this item.")
        alertDialogBuilder.setNeutralButton("OK") { dialog, which ->
            dialog.dismiss()
            val manager = activity.supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, ScanFragment())
            transaction.commit()
        }
        alertDialogBuilder.show()
    }
}