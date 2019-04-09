package net.corpy.corpyecommerce.activities.checkout

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import com.hazem.utilslib.libs.UtilsFunctions
import kotlinx.android.synthetic.main.checkout_activity.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.adapters.CitiesAdapter
import net.corpy.corpyecommerce.adapters.adapter.CartListAdapter
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Cities
import net.corpy.corpyecommerce.model.Data
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest
import net.corpy.corpyecommerce.network.requestModel.PlaceOrderRequest

class CheckOutActivity : BaseActivity<CheckOutPresenter>(), CheckOutView, ToolbarInit {
    override fun onCartLoaded(data: Data?) {
        no_item_layout?.visibility = View.GONE
        cartAdapter?.submitList(data?.cart ?: ArrayList())
        cartAdapter?.notifyDataSetChanged()

        if (data?.cart?.isEmpty() == true) {
            no_item_layout?.visibility = View.VISIBLE
        }
    }

    var adapter: CitiesAdapter? = null
    override fun onCitiesLoaded(list: List<Cities>) {
        adapter = CitiesAdapter(list)
        cities_spinner?.adapter = adapter
    }

    override fun initPresenter(): CheckOutPresenter {
        return CheckOutPresenter(this)
    }

    override fun initViews() {
        submit_order?.setOnClickListener { placeMyOrder() }
        loadCartData()


        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }

    var id = "0"

    var cartAdapter: CartListAdapter? = null

    private fun loadCartData() {

        id = App.instance.userResponse?.data?.user?.id?.toString().toString()

        cartAdapter = CartListAdapter(object : ItemClickListener {
            override fun addToCart(addToCartRequest: AddToCartRequest) {}

            override fun onClick(position: Int) {
                val item = cartAdapter?.getItemAt(position)
                if (item != null) {
                    EcommerceRepo.removeItemFromCart(item.id.toString()) { status, msg ->
                        if (status) {
                            Utils.loadCartCount()
                            presenter.loadCart(id)
                            Utils.showSuccessDialog(getCurrentContext()
                                    , getCurrentContext()
                                    .getString(R.string.item_removed)
                                    , msg, true) {}
                        } else {
                            Utils.showErrorDialog(getCurrentContext()
                                    , getCurrentContext()
                                    .getString(R.string.oops)
                                    , msg, cancellable = true) {}
                        }
                    }
                }
            }
        })
        cart_recycler_view?.setHasFixedSize(true)
        cart_recycler_view?.layoutManager =
                LinearLayoutManager(getCurrentContext(), LinearLayoutManager.VERTICAL, false)
        cart_recycler_view?.adapter = cartAdapter

    }

    private fun placeMyOrder() {
        if (!validateInputs()) return

        val placeOrderRequest = PlaceOrderRequest()
        placeOrderRequest.userId = App.instance.userResponse?.data?.user?.id?.toString() ?: ""
        placeOrderRequest.name = name_et.text.toString()
        placeOrderRequest.email = email_et.text.toString()
        placeOrderRequest.address = address_et.text.toString()
        placeOrderRequest.city = getSelectedItemId()
        placeOrderRequest.phone = phone_et.text.toString()

        onLoading("")
        EcommerceRepo.placeOrder(placeOrderRequest) { status, msg ->
            finishLoad()
            if (status) {
                Utils.showSuccessDialog(this, getString(R.string.order_done), msg) { finish() }
                App.cartUpTodate = false
            } else {
                Utils.showErrorDialog(this, getString(R.string.oops), msg, cancellable = true) {}
            }
        }

    }

    private fun getSelectedItemId(): String {
        return adapter?.cities?.get(cities_spinner.selectedItemPosition)?.id?.toString() ?: ""
    }

    private fun validateInputs(): Boolean {
        if (!UtilsFunctions.validateETNotEmpty(name_et, getString(R.string.emptyEditText))) return false
        if (!UtilsFunctions.validateETNotEmpty(address_et, getString(R.string.emptyEditText))) return false
        if (!UtilsFunctions.validateETNotEmpty(phone_et, getString(R.string.emptyEditText))) return false
        if (!UtilsFunctions.validateETNotEmpty(email_et, getString(R.string.emptyEditText))) return false
        return true
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCities()
        presenter.loadCart(id)
    }

    override fun loadResourceLayout(): Int {
        return R.layout.checkout_activity
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
        return getString(R.string.check_out)
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }
}