package net.corpy.corpyecommerce.activities.search

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.search_layout.*
import net.corpy.corpyecommerce.*
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.activities.productDetail.ProductDetailsActivity
import net.corpy.corpyecommerce.adapters.AllProductsAdapter
import net.corpy.corpyecommerce.interfaces.ProductClickListener
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest


class SearchActivity : BaseActivity<SearchPresenter>(), SearchView, ToolbarInit {
    override fun onSearchDataLoaded(list: List<Product>) {
        productsAdapter?.products = list
        productsAdapter?.notifyDataSetChanged()
        if (list.isEmpty()) {
            lyt_no_result?.visibility = View.VISIBLE
            lyt_suggestion?.visibility = View.GONE
        } else {
            lyt_no_result?.visibility = View.GONE
            lyt_suggestion?.visibility = View.VISIBLE
        }

    }

    override fun initPresenter(): SearchPresenter {
        return SearchPresenter(this)
    }

    override fun initViews() {
        initialRecycler()
        et_search?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchText(s?.toString() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }

    var productsAdapter: AllProductsAdapter? = null

    private fun initialRecycler() {
        productsAdapter = AllProductsAdapter(ArrayList(), object : ProductClickListener {
            override fun addToCart(addToCartRequest: AddToCartRequest) {
                showMessage(getString(R.string.adding_to_cart))
                addToCartRequest.lang = App.getCurrentLanguageKey(getCurrentContext())
                EcommerceRepo.addItemToCart(addToCartRequest) { status, msg ->
                    if (status) {
                        Utils.loadCartCount()
                        Utils.showSuccessDialog(getCurrentContext()
                                , getCurrentContext()
                                .getString(R.string.add_to_cart)
                                , msg, true) {}
                    } else {
                        Utils.showErrorDialog(getCurrentContext()
                                , getCurrentContext()
                                .getString(R.string.oops)
                                , msg, cancellable = true) {}
                    }
                }
            }

            override fun onProductClick(product: Product, view: View?) {
                val intent = Intent(getCurrentActivity(), ProductDetailsActivity::class.java)
                intent.putExtra(Product.PRODUCT_KEY, product)
//                startActivity(intent)
                navigateToActivityWithTransaction(getCurrentActivity(), intent, view)

            }
        })
        search_products_recycler?.setHasFixedSize(true)
        search_products_recycler?.layoutManager =
                GridLayoutManager(getCurrentContext(), 2)
        search_products_recycler?.adapter = productsAdapter
    }

    private fun searchText(s: String) {
        presenter.searchText(s)
    }

    override fun loadResourceLayout(): Int {
        return R.layout.search_layout
    }

    override fun onMenuItemClicked(itemId: Int) {
    }

    override fun showMessage(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }

    override fun finishLoad() {
        progress_bar?.visibility = View.GONE
    }

    override fun onLoading(msg: String) {
        progress_bar?.visibility = View.VISIBLE
        lyt_no_result?.visibility = View.GONE
        lyt_suggestion?.visibility = View.GONE
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
        return ""
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }
}