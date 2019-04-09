package net.corpy.corpyecommerce.fragments.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hazem.mvpbase.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.productDetail.ProductDetailsActivity
import net.corpy.corpyecommerce.activities.products.ProductsActivity
import net.corpy.corpyecommerce.adapters.AdViewPagerAdapter
import net.corpy.corpyecommerce.adapters.BrandsAdapter
import net.corpy.corpyecommerce.adapters.ProductsAdapter
import net.corpy.corpyecommerce.interfaces.BrandClickListener
import net.corpy.corpyecommerce.interfaces.ProductClickListener
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.model.Slider
import net.corpy.corpyecommerce.model.SubCategories
import net.corpy.corpyecommerce.navigateToActivityWithTransaction
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.AddToCartRequest

class HomeFragment : BaseFragment<HomeFragmentPresenter>(), HomeFragmentView, ProductClickListener {
    var oldTap = 0

    override fun getSavedAdsList(): List<Slider> {
        return adsProducts
    }

    override fun getSavedBestSellerList(): List<Product> {
        return bestSellerList
    }

    override fun getSavedFeaturedList(): List<Product> {
        return if (oldTap == 0)
            productsTabsList
        else emptyList()
    }

    override fun getSavedOnSaleList(): List<Product> {
        return productsTabsList
    }

    override fun getSavedTopRatedList(): List<Product> {
        return if (oldTap == 2)
            productsTabsList
        else emptyList()
    }

    override fun getSavedMostLikedList(): List<Product> {
        return if (oldTap == 1)
            productsTabsList
        else emptyList()
    }

    override fun getSavedRecommendedList(): List<Product> {
        return recommendedList
    }

    override fun getSavedBrandsList(): List<SubCategories> {
        return brandsList
    }

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
        navigateToActivityWithTransaction(getCurrentActivity(), intent, view)
//        startActivity(intent)
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onLoadingFailed(msg: String) {

    }

    private var visibleToUser: Boolean = false
    override fun getCurrentContext(): Context {
        return context!!
    }

    override fun getCurrentActivity(): Activity {
        return activity!!
    }

    override fun setResourceLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initPresenter(): HomeFragmentPresenter {
        return HomeFragmentPresenter(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser && isResumed) {
            oldTap = products_tabs.selectedTabPosition
            Utils.loadCartCount()
            visibleToUser = isVisibleToUser
            if (!App.cartUpTodate) {

                if (adsAdapter.count == 0) {
                    presenter.loadAds()
                }

                if (adapterTabs.itemCount == 0) {
                    presenter.tabPositionChanged(products_tabs.selectedTabPosition)
                }

                if (bestSellerAdapter.itemCount == 0) {
                    presenter.loadBestSeller()
                }

                if (recommendedAdapter.itemCount == 0) {
                    presenter.loadRecommendedProducts()
                }

                if (brandsAdapter.itemCount == 0) {
                    presenter.loadBrands()
                }
            }
        }

    }

    // ads variables
    private var adsProducts: List<Slider> = ArrayList()
    private lateinit var adsAdapter: AdViewPagerAdapter
    private lateinit var customHandler: Handler
    private var currentPage = 0
    private lateinit var runnable: Runnable

    // tabs products variables
    private var productsTabsList: List<Product> = ArrayList()
    private lateinit var adapterTabs: ProductsAdapter

    // brands variables
    private lateinit var brandsAdapter: BrandsAdapter
    private var brandsList: List<SubCategories> = ArrayList()


    override fun onAdsLoaded(products: List<Slider>) {

        main_swipe.isRefreshing = false

        adsProducts = products
        ads_progress_bar?.visibility = View.GONE
        adsAdapter.setDataList(products)
        indicator?.setViewPager(ads_view_pager)

        if (products.isEmpty()) {
            ads_error_loading_data.visibility = View.VISIBLE
        } else {
            ads_error_loading_data.visibility = View.GONE
        }

    }


    override fun productsLoaded(products: List<Product>) {
        productsTabsList = products
        products_progress_bar?.visibility = View.GONE
        products_recycler_view?.visibility = View.VISIBLE
        adapterTabs.products = products
        adapterTabs.notifyDataSetChanged()


        if (products.isEmpty()) {
            products_error_loading_data.visibility = View.VISIBLE
        } else {
            products_error_loading_data.visibility = View.GONE
        }

    }

    override fun onBestSellerLoaded(product: List<Product>) {
        bestSellerList = product
        best_seller_progress_bar?.visibility = View.GONE
        bestSellerAdapter.products = product
        bestSellerAdapter.notifyDataSetChanged()

        if (product.isEmpty()) {
            best_seller_error_loading_data.visibility = View.VISIBLE
        } else {
            best_seller_error_loading_data.visibility = View.GONE
        }

    }

    override fun onRecommendedLoaded(list: List<Product>) {
        recommendedList = list
        recommended_products_progress_bar?.visibility = View.GONE
        recommendedAdapter.products = list
        recommendedAdapter.notifyDataSetChanged()

        if (list.isEmpty()) {
            recommended_error_loading_data.visibility = View.VISIBLE
        } else {
            recommended_error_loading_data.visibility = View.GONE
        }
    }

    override fun onBrandsLoaded(brands: List<SubCategories>) {
        brandsList = brands
        brand_progress_bar?.visibility = View.GONE
        brandsAdapter.department = brands
        brandsAdapter.notifyDataSetChanged()
        if (brands.isEmpty()) {
            brand_error_loading_data.visibility = View.VISIBLE
        } else {
            brand_error_loading_data.visibility = View.GONE
        }
    }


    override fun initViews() {

        main_swipe.setOnRefreshListener {

            adsProducts = emptyList()
            bestSellerList = emptyList()
            productsTabsList = emptyList()
            brandsList = emptyList()
            recommendedList = emptyList()



            oldTap = products_tabs.selectedTabPosition
            presenter.loadAds()
            presenter.tabPositionChanged(products_tabs.selectedTabPosition)
            presenter.loadBestSeller()
            presenter.loadRecommendedProducts()
            presenter.loadBrands()
        }

        loadAds()
        initTabs()
        loadBestSeller()
        loadRecommendedProducts()
        loadBrands()

        ads_error_loading_data?.setOnClickListener {
            ads_error_loading_data?.visibility = View.GONE
            presenter.loadAds()
        }
        products_error_loading_data?.setOnClickListener {
            products_error_loading_data?.visibility = View.GONE
            presenter.tabPositionChanged(products_tabs.selectedTabPosition)
        }
        best_seller_error_loading_data?.setOnClickListener {
            best_seller_error_loading_data?.visibility = View.GONE
            presenter.loadBestSeller()
        }
        recommended_error_loading_data?.setOnClickListener {
            recommended_error_loading_data?.visibility = View.GONE
            presenter.loadRecommendedProducts()
        }
        brand_error_loading_data?.setOnClickListener {
            brand_error_loading_data?.visibility = View.GONE
            presenter.loadBrands()
        }

    }

    override fun onResume() {
        super.onResume()

        ads_error_loading_data?.visibility = View.GONE
        products_error_loading_data?.visibility = View.GONE
        best_seller_error_loading_data?.visibility = View.GONE
        recommended_error_loading_data?.visibility = View.GONE
        brand_error_loading_data?.visibility = View.GONE


        oldTap = products_tabs.selectedTabPosition

        presenter.loadAds()
        presenter.tabPositionChanged(products_tabs.selectedTabPosition)
        presenter.loadBestSeller()
        presenter.loadRecommendedProducts()
        presenter.loadBrands()
    }

    private var recommendedList: List<Product> = ArrayList()
    private lateinit var recommendedAdapter: ProductsAdapter

    private fun loadRecommendedProducts() {
        recommendedAdapter = ProductsAdapter(recommendedList, this)
        recommended_products_recycler?.setHasFixedSize(true)
        recommended_products_recycler?.layoutManager =
                LinearLayoutManager(getCurrentContext(), LinearLayoutManager.HORIZONTAL, false)
        recommended_products_recycler?.adapter = recommendedAdapter
    }


    // top Category variables
    private var bestSellerList: List<Product> = ArrayList()
    private lateinit var bestSellerAdapter: ProductsAdapter

    private fun loadBestSeller() {
        bestSellerAdapter = ProductsAdapter(bestSellerList, this)
        best_seller_recycler?.setHasFixedSize(true)
        best_seller_recycler?.layoutManager =
                LinearLayoutManager(getCurrentContext(), LinearLayoutManager.HORIZONTAL, false)
        best_seller_recycler?.adapter = bestSellerAdapter
    }


    private fun loadBrands() {

        brandsAdapter = BrandsAdapter(brandsList, object : BrandClickListener {
            override fun onBrandClick(brand: SubCategories) {
                startActivity(Intent(context, ProductsActivity::class.java)
                        .putExtra("id", brand.id.toString())
                        .putExtra("ar_name", brand.ar_name)
                        .putExtra("en_name", brand.en_name))
            }
        })

        top_brand_recycler?.setHasFixedSize(true)
        top_brand_recycler?.layoutManager =
                LinearLayoutManager(getCurrentContext(), LinearLayoutManager.HORIZONTAL, false)
        top_brand_recycler?.adapter = brandsAdapter

    }

    override fun loadFeatureProducts() {
        presenter.loadFeatureProducts()
    }

    override fun loadOnSaleProducts() {
        presenter.loadOnSaleProducts()
    }

    override fun loadTopRatedProducts() {
        presenter.loadTopRatedProducts()
    }

    override fun loadMostLikedProducts() {
        presenter.loadMostLikedProducts()
    }

    private fun initTabs() {
        adapterTabs = ProductsAdapter(productsTabsList, this)
        products_recycler_view?.setHasFixedSize(true)
        products_recycler_view?.layoutManager =
                LinearLayoutManager(getCurrentContext(), LinearLayoutManager.HORIZONTAL, false)
        products_recycler_view?.adapter = adapterTabs


        products_tabs?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                products_recycler_view?.scrollToPosition(0)
                presenter.tabPositionChanged(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                oldTap = tab.position
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })


    }

    private fun loadAds() {
        adsAdapter = AdViewPagerAdapter(getCurrentContext(), adsProducts)
        ads_view_pager?.adapter = adsAdapter
        indicator?.setViewPager(ads_view_pager)
        customHandler = Handler()
        runnable = Runnable {
            if (visibleToUser) {
                if (currentPage == adsProducts.size) {
                    currentPage = 0
                }
                ads_view_pager?.setCurrentItem(currentPage++, true)
                customHandler.postDelayed(runnable, 2500)
            }
        }
        customHandler.postDelayed(runnable, 0)
    }


    override fun onLoading(msg: String) {
        when (msg) {
            HomeFragmentPresenter.ADS_LOADING -> ads_progress_bar?.visibility = View.VISIBLE
            HomeFragmentPresenter.PRODUCT_LOADING -> {
                products_recycler_view?.visibility = View.GONE
                products_progress_bar?.visibility = View.VISIBLE
            }
            HomeFragmentPresenter.BEST_SELLER_LOADING -> best_seller_progress_bar?.visibility = View.VISIBLE
            HomeFragmentPresenter.BRAND_LOADING -> brand_progress_bar?.visibility = View.VISIBLE
            HomeFragmentPresenter.RECOMMENDED_LOADING -> recommended_products_progress_bar?.visibility = View.VISIBLE
        }

    }

    override fun finishLoad() {

    }

    override fun showMessage(msg: String) {
        Snackbar.make(rootView!!, msg, Snackbar.LENGTH_LONG).show()
    }


}