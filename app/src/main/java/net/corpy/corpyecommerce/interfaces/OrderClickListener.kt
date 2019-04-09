package net.corpy.corpyecommerce.interfaces

import net.corpy.corpyecommerce.model.Order

interface OrderClickListener {
    fun onOrderClicked(order: Order)
}