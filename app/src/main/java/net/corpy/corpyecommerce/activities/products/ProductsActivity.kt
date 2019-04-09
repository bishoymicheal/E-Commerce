package net.corpy.corpyecommerce.activities.products

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.products_activity.*
import net.corpy.corpyecommerce.*
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.activities.productDetail.ProductDetailsActivity
import net.corpy.corpyecommerce.adapters.AllProductsAdapter
import net.corpy.corpyecommerce.interfaces.ProductClickListener
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest

class ProductsActivity : BaseActivity<ProductsPresenter>(), ProductsView, ToolbarInit, ProductClickListener {
    override fun addToCart(addToCartRequest: AddToCartRequest) {
        showMessage(getString(R.string.add_to_cart))
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
//        startActivity(intent)
        navigateToActivityWithTransaction(getCurrentActivity(), intent, view)
    }

    override fun onProductsLoaded(status: Boolean, list: List<Product>) {
        no_item_layout?.visibility = View.GONE
        productAdapter?.products = list
        productAdapter?.notifyDataSetChanged()

        if (status && list.isEmpty()) {
            no_item_layout?.visibility = View.VISIBLE
        }

        if (!status) {
            error_loading_data.visibility = View.VISIBLE
        } else {
            error_loading_data.visibility = View.GONE
        }
    }

    var productAdapter: AllProductsAdapter? = null
    override fun initPresenter(): ProductsPresenter {
        return ProductsPresenter(this)
    }

    var id = "0"
    var arname = ""
    var enname = ""

    override fun initViews() {

        id = intent.getStringExtra("id")
        arname = intent.getStringExtra("ar_name")
        enname = intent.getStringExtra("en_name")


        productAdapter = AllProductsAdapter(ArrayList(), this)
        productsRecyclerView?.layoutManager = GridLayoutManager(this, 2)
        productsRecyclerView?.setHasFixedSize(true)
        productsRecyclerView?.adapter = productAdapter

        error_loading_data.setOnClickListener {
            presenter.loadProducts(id, arname, enname)
        }

        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }

    override fun onResume() {
        super.onResume()
        presenter.loadProducts(id, arname, enname)
    }

    override fun loadResourceLayout(): Int {
        return R.layout.products_activity
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
        id = intent.getStringExtra("id")
        arname = intent.getStringExtra("ar_name")
        enname = intent.getStringExtra("en_name")

        if (App.getCurrentLanguageKey(this) == "ar")
            return arname
        return enname
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }
}