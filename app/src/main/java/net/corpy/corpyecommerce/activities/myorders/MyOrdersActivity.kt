package net.corpy.corpyecommerce.activities.myorders

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.my_orders_activity.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.activities.login.LoginActivity
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.activities.orderdetails.OrderDetailsActivity
import net.corpy.corpyecommerce.adapters.OrdersAdapter
import net.corpy.corpyecommerce.interfaces.OrderClickListener
import net.corpy.corpyecommerce.model.Order

class MyOrdersActivity : BaseActivity<MyOrdersPresenter>(), MyOrdersView, ToolbarInit, OrderClickListener {


    override fun onOrderClicked(order: Order) {
        val intent = Intent(getCurrentActivity(), OrderDetailsActivity::class.java)
        intent.putExtra("order", order)
        startActivity(intent)
    }

    override fun onOrdersLoaded(list: List<Order>) {
        no_item_layout?.visibility = View.GONE
        ordersAdapter?.products = list
        ordersAdapter?.notifyDataSetChanged()

        if (list.isEmpty()) {
            no_item_layout?.visibility = View.VISIBLE
        }

    }


    override fun initPresenter(): MyOrdersPresenter {
        return MyOrdersPresenter(this)
    }

    var id = "0"
    var ordersAdapter: OrdersAdapter? = null

    override fun initViews() {

        if (App.instance.userResponse == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            overridePendingTransition(0, 0)
        }

        id = App.instance.userResponse?.data?.user?.id?.toString().toString()

        ordersAdapter = OrdersAdapter(ArrayList(), this)
        ordersRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ordersRecyclerView?.setHasFixedSize(true)
        ordersRecyclerView?.adapter = ordersAdapter


        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }

    override fun onResume() {
        super.onResume()

        presenter.loadOrders(id)

    }

    override fun loadResourceLayout(): Int {

        return R.layout.my_orders_activity

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
        return getString(R.string.my_orders)
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }
}