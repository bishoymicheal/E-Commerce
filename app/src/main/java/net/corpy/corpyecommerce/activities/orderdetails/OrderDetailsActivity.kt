package net.corpy.corpyecommerce.activities.orderdetails

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.orders_details_activity.*
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.adapters.OrderDetailsAdapter
import net.corpy.corpyecommerce.model.Order
import net.corpy.corpyecommerce.model.OrdersItem

class OrderDetailsActivity : BaseActivity<OrderDetailsPresenter>(), OrderDetailsView, ToolbarInit {
    override fun onOrderDetailsLoaded(list: List<OrdersItem>) {
        cartAdapter?.products = list
        cartAdapter?.notifyDataSetChanged()

        orders_count?.text =  list.size.toString() +" "+ getString(R.string.item_string)

    }

    override fun initPresenter(): OrderDetailsPresenter {
        return OrderDetailsPresenter(this)
    }

    var order: Order? = null
    var cartAdapter: OrderDetailsAdapter? = null

    override fun initViews() {

        order = intent.getSerializableExtra("order") as Order
        if (order == null) finish()


        order_number?.text = getString(R.string.order_no) + order?.code
        total_price?.text = order?.price.toString() + " " + getString(R.string.price_label)
        order_status_value?.text = order?.level.toString()


        cartAdapter = OrderDetailsAdapter(ArrayList())
        orders_items_recycler?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        orders_items_recycler?.setHasFixedSize(true)
        orders_items_recycler?.adapter = cartAdapter

    }

    override fun onResume() {
        super.onResume()

        presenter.loadOrderDetails(order?.id?.toString() ?: "")

    }

    override fun loadResourceLayout(): Int {
        return R.layout.orders_details_activity
    }

    override fun onMenuItemClicked(itemId: Int) {

    }

    override fun showMessage(msg: String) {
    }

    override fun finishLoad() {
        progress_bar?.visibility = View.GONE
    }

    override fun onLoading(msg: String) {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun onLoadingFailed(msg: String) {
    }

    override fun getToolbarId(): Int {
        return R.id.toolbar
    }

    override fun getToolbarTextColor(): Int {
        return R.color.toolbarTextColor
    }

    override fun initToolbarTitle(): String {
        return getString(R.string.order_details)
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }
}