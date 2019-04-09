package net.corpy.corpyecommerce.fragments.cart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.checkout.CheckOutActivity
import net.corpy.corpyecommerce.adapters.adapter.CartListAdapter
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Data
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest
import java.util.*

class CartFragment : BaseFragment<CartFragmentPresenter>(), CartFragmentView {
    override fun onCartLoaded(data: Data?) {
        cart_swipe.isRefreshing = false

        App.cartCountChanged = false
        no_item_layout?.visibility = View.GONE
        cartAdapter?.submitList(data?.cart ?: ArrayList())
//        cartAdapter?.notifyDataSetChanged()

        total_price?.text = (data?.totalprice?.toString()
                ?: "") + " " + getString(R.string.currency)
        total_weight?.text = (data?.totalweight?.toString() ?: "") + " " + getString(R.string.kg)
        if (data?.cart?.isEmpty() == true) {
            no_item_layout?.visibility = View.VISIBLE
        }

        if (data == null) {
            error_loading_data.visibility = View.VISIBLE
        } else {
            error_loading_data.visibility = View.GONE
        }

    }

    var id = "0"
    override fun onLoadingFailed(msg: String) {

    }

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun setResourceLayout(): Int {
        return R.layout.fragment_cart
    }

    override fun initPresenter(): CartFragmentPresenter {
        return CartFragmentPresenter(this)
    }

    override fun initViews() {


        cart_swipe.setOnRefreshListener {
            id = App.instance.userResponse?.data?.user?.id?.toString().toString()
            presenter.loadCart(id)
        }

        check_out?.setOnClickListener {
            startActivity(Intent(context, CheckOutActivity::class.java))
        }
        loadCartData()

        error_loading_data.setOnClickListener {
            id = App.instance.userResponse?.data?.user?.id?.toString().toString()
            presenter.loadCart(id)
        }
    }

    override fun onResume() {
        super.onResume()
        id = App.instance.userResponse?.data?.user?.id?.toString().toString()
        presenter.loadCart(id)
    }

    var cartAdapter: CartListAdapter? = null
    private fun loadCartData() {
        id = App.instance.userResponse?.data?.user?.id?.toString().toString()
        cartAdapter = CartListAdapter(object : ItemClickListener {
            override fun addToCart(addToCartRequest: AddToCartRequest) {}
            override fun onClick(position: Int) {
                val item = cartAdapter?.getItemAt(position)
                if (item != null) {
                    showMessage(getString(R.string.deleting_product))
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
                                    , msg,cancellable = true) {}
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

    override fun onLoading(msg: String) {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun finishLoad() {
        progress_bar?.visibility = View.GONE

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && isResumed) {
            if (!App.cartUpTodate) {
                Utils.loadCartCount()
                id = App.instance.userResponse?.data?.user?.id?.toString().toString()
                presenter.loadCart(id)
            }

            if (App.cartCountChanged) {
                id = App.instance.userResponse?.data?.user?.id?.toString().toString()
                presenter.loadCart(id)
            }

            if (cartAdapter?.itemCount == 0) {
                id = App.instance.userResponse?.data?.user?.id?.toString().toString()
                presenter.loadCart(id)
            }

        }
    }

    override fun showMessage(msg: String) {
        Snackbar.make(rootView!!, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun getCurrentContext(): Context {
        return context!!
    }

    override fun getCurrentActivity(): Activity {
        return activity!!
    }
}