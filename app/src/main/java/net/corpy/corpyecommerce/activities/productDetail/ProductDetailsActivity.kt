package net.corpy.corpyecommerce.activities.productDetail

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_tap_layout.*
import kotlinx.android.synthetic.main.product_details_activity.*
import net.corpy.corpyecommerce.*
import net.corpy.corpyecommerce.activities.home.HomeActivity
import net.corpy.corpyecommerce.activities.login.LoginActivity
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.adapters.ColorsRecyclerViewAdapter
import net.corpy.corpyecommerce.adapters.ImagesRecyclerViewAdapter
import net.corpy.corpyecommerce.adapters.SizesRecyclerViewAdapter
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Data
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest
import net.corpy.corpyecommerce.widget.BadgeView

class ProductDetailsActivity : BaseActivity<ProductDetailsPresenter>(), ProductDetailsView, ToolbarInit, ItemClickListener, ViewPager.OnPageChangeListener {
    override fun addToCart(addToCartRequest: AddToCartRequest) {

    }

    override fun onLoadingFailed(msg: String) {

    }

    override fun getToolbarId(): Int {
        return R.id.toolbar
    }

    override fun getToolbarTextColor(): Int {
        return R.color.toolbarTextColor
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    private lateinit var imagesRecyclerViewAdapter: ImagesRecyclerViewAdapter
    override fun onPageSelected(position: Int) {
        product_images_recycler_view?.scrollToPosition(position)
        imagesRecyclerViewAdapter.setSelectedPosition(position)
    }

    override fun onClick(position: Int) {
        Picasso.get().load(Constants.BASE_IMAGES_URL + imagesList?.get(position))
                .error(R.drawable.error).fit().into(product_image, object : Callback {
                    override fun onError(e: Exception?) {
                        progress_bar?.visibility = View.GONE
                    }

                    override fun onSuccess() {
                        progress_bar?.visibility = View.GONE
                    }
                })
    }


    var product: Product? = null

    override fun initToolbarTitle(): String {
        product = intent.getSerializableExtra(Product.PRODUCT_KEY) as Product
        return ""
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }

    override fun loadResourceLayout(): Int {
        return R.layout.product_details_activity
    }

    override fun initPresenter(): ProductDetailsPresenter {
        return ProductDetailsPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        ProductDetailsActivity.cartCount = cart_count
        Utils.updateCartTabNumber(App.cartCount)
        loadCategoryName()
        images_progress_bar?.visibility = View.VISIBLE
        EcommerceRepo.loadSingleProduct(product?.id.toString()) { status, data ->
            images_progress_bar?.visibility = View.GONE
            productData = data
            loadImages()
            loadColors()
            loadSizes()
        }
        Picasso.get().load(Constants.BASE_IMAGES_URL + product?.photo)
                .error(R.drawable.error).fit().into(product_image, object : Callback {
                    override fun onError(e: Exception?) {
                        progress_bar?.visibility = View.GONE
                    }

                    override fun onSuccess() {
                        progress_bar?.visibility = View.GONE
                    }
                })

        if (App.getCurrentLanguageKey(this) == "ar") {
            product_title?.text = product?.ar_title ?: "المنتج"
            description_text?.text = product?.ar_content ?: "المنتج"
            product_price?.text = product?.price.toString() + " جنية"
        } else {
            product_title?.text = product?.en_title ?: "product"
            description_text?.text = product?.en_content ?: "product"
            product_price?.text = product?.price.toString() + " LE"
        }
    }

    var productData: Data? = null
    override fun initViews() {
        if (product == null) {
            Utils.showErrorDialog(getCurrentContext()
                    , getCurrentContext()
                    .getString(R.string.oops)
                    , getString(R.string.error_happened), cancellable =false) { finish() }

        }

        if (App.getCurrentLanguageKey(this) == "ar")
            toolbar_text_title.text = product?.ar_title ?: "المنتج"
        toolbar_text_title.text = product?.en_title ?: "product"


        bt_add_to_cart?.setOnClickListener {
            if (App.instance.userResponse?.data?.user != null) {
                val addToCartRequest = AddToCartRequest()
                addToCartRequest.productId = product?.id.toString()
                addToCartRequest.userId = App.instance.userResponse?.data?.user?.id.toString()
                addToCartRequest.lang = App.getCurrentLanguageKey(getCurrentContext())
                showMessage(getString(R.string.add_to_cart))
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
                                , msg, cancellable =true) {}
                    }
                }
            } else {
                Utils.showWarningDialog(this,
                        getString(R.string.login),
                        getString(R.string.login_frist),
                        true) {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
        bt_add_to_wishlist?.setOnClickListener {
            if (App.instance.userResponse?.data?.user != null) {
                val addToCartRequest = AddToCartRequest()
                addToCartRequest.productId = product?.id.toString()
                addToCartRequest.userId = App.instance.userResponse?.data?.user?.id.toString()
                showMessage(getString(R.string.add_to_wishlist))
                EcommerceRepo.addItemToWishList(addToCartRequest) { status, msg ->
                    if (status) {
                        Utils.showSuccessDialog(getCurrentContext()
                                , getCurrentContext()
                                .getString(R.string.add_to_wishlist)
                                , msg, true) {}
                    } else {
                        Utils.showErrorDialog(getCurrentContext()
                                , getCurrentContext()
                                .getString(R.string.oops)
                                , msg, cancellable =true) {}
                    }
                }
            } else {
                Utils.showWarningDialog(this,
                        getString(R.string.login),
                        getString(R.string.login_frist),
                        true) {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }


        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

        cart_button.setOnClickListener {
            HomeActivity.goToCart = true
            finish()
        }

        ProductDetailsActivity.cartCount = cart_count
        cart_title.visibility  = View.GONE
    }

    companion object {
        var cartCount: BadgeView? = null
    }


    private var sizesList: ArrayList<String>? = null
    private lateinit var sizesRecyclerViewAdapter: SizesRecyclerViewAdapter
    private fun loadSizes() {
        sizesList = productData?.size ?: ArrayList()
        sizesList?.add(0, product?.size ?: "")
        sizesRecyclerViewAdapter = SizesRecyclerViewAdapter(sizesList ?: ArrayList())

        sizes_recycler_view?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sizes_recycler_view?.setHasFixedSize(true)
        sizes_recycler_view?.adapter = sizesRecyclerViewAdapter
    }

    private fun loadCategoryName() {
        EcommerceRepo.loadProductCategory(product?.dep_id?.toString() ?: "") { status, productCat ->
            if (App.getCurrentLanguageKey(this) == "ar") {
                product_parent?.text = productCat?.ar_name ?: product?.ar_title
            } else {
                product_parent?.text = productCat?.en_name ?: product?.en_title
            }
        }
    }

    private var colorsList: ArrayList<String>? = null
    private lateinit var colorsRecyclerViewAdapter: ColorsRecyclerViewAdapter
    private fun loadColors() {
        colorsList = productData?.color ?: ArrayList()
        colorsList?.add(0, product?.color ?: "")
        colorsRecyclerViewAdapter = ColorsRecyclerViewAdapter(colorsList ?: ArrayList())
        colors_recycler_view?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colors_recycler_view?.setHasFixedSize(true)
        colors_recycler_view?.adapter = colorsRecyclerViewAdapter
    }

    private var imagesList: ArrayList<String>? = null

    private fun loadImages() {
        imagesList = productData?.gallary ?: ArrayList()
        imagesList?.add(0, product?.photo ?: "")

        imagesRecyclerViewAdapter = ImagesRecyclerViewAdapter(imagesList ?: ArrayList(), this)
        product_images_recycler_view?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        product_images_recycler_view?.setHasFixedSize(true)
        product_images_recycler_view?.adapter = imagesRecyclerViewAdapter
    }

    override fun onMenuItemClicked(itemId: Int) {
    }

    override fun showMessage(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onLoading(msg: String) {

    }

    override fun finishLoad() {
    }
}