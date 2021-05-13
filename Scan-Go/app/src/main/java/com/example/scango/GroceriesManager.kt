package com.example.scango

object GroceriesManager {
    private var products: ArrayList<Product> = arrayListOf<Product>()
    private var totalPrice: Double = 0.0


    init {
        setTotalPrice()
    }

    fun getProducts() : ArrayList<Product>{
        return products
    }

    fun addToProducts(product: Product) {
        for (p : Product in products)
        {
            if(p.getProductName() == product.getProductName())
            {
                p.increaseAmount()
                setTotalPrice()
                return
            }
        }
        products.add(product)
        setTotalPrice()
    }

    fun setTotalPrice() : Double {
        totalPrice = 0.00
        for(p: Product in products)
        {
            totalPrice += p.getTotalPrice()
        }
        return totalPrice
    }

    fun getTotalPrice() : Double {
        return totalPrice
    }
}