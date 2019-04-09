package net.corpy.corpyecommerce.activities.orderdetails

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.OrdersItem

interface OrderDetailsView : LoadingView {
    fun onOrderDetailsLoaded(list: List<OrdersItem>)
}