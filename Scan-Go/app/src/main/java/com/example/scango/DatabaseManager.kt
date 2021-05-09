package com.example.scango

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

object DatabaseManager {
    private var databaseReference: DatabaseReference = Firebase.database.reference


    init {
        Log.e("Database connection created", "!")
    }

    fun setProduct() {
        val testProduct = Product("Product", 1.75)
        databaseReference.child("Products").child("9999").setValue(testProduct).addOnFailureListener {
            Log.e("firebase: ", "error writing data", it)
        }
    }
}