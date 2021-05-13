package com.example.scango

class Product(private val productName: String, private var price: Double, private var amount: Int, private var totalPrice: Double) {

    fun getProductName() : String {
        return productName
    }

    fun getPrice() : Double {
        return price
    }

    fun getTotalPrice() : Double {
        return totalPrice
    }

    fun getAmount() : Int {
        return amount
    }

    fun increaseAmount() {
        this.totalPrice += this.price
        this.amount++
    }

    fun decreaseAmount() {
        this.totalPrice -= this.price;
        this.amount--
    }
}
