package net.corpy.corpyecommerce.fragments.home

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.network.EcommerceRepo

class HomeFragmentPresenter(view: HomeFragmentView) : BasePresenter<HomeFragmentView>(view) {
    companion object {
        const val ADS_LOADING = "ads_loading"
        const val PRODUCT_LOADING = "PRODUCT_LOADING"
        const val RECOMMENDED_LOADING = "RECOMMENDED_LOADING"
        const val BEST_SELLER_LOADING = "BEST_SELLER_LOADING"
        const val BRAND_LOADING = "BRAND_LOADING"
    }

    override fun resume() {
    }

    override fun pause() {

    }

    override fun create(savedInstance: Bundle?) {

    }

    override fun destroy() {

    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun loadAds() {
        view.onLoading(ADS_LOADING)

        if (view.getSavedAdsList().isEmpty()) {
            EcommerceRepo.getSlider { status, list ->
                view.onAdsLoaded(list)
            }
        } else {
            view.onAdsLoaded(view.getSavedAdsList())
        }
    }

    fun tabPositionChanged(currentPosition: Int) {
        when (currentPosition) {
            0 -> view.loadFeatureProducts()
            1 -> view.loadMostLikedProducts()
            2 -> view.loadTopRatedProducts()
        }
    }

    fun loadBestSeller() {
        view.onLoading(BEST_SELLER_LOADING)

        if (view.getSavedBestSellerList().isEmpty()) {
            EcommerceRepo.bestSeller { status, list ->
                view.onBestSellerLoaded(list)
            }
        } else {
            view.onBestSellerLoaded(view.getSavedBestSellerList())
        }

    }

    fun loadBrands() {
        view.onLoading(BRAND_LOADING)
        if (view.getSavedBrandsList().isEmpty()) {
            EcommerceRepo.getBrand { status, list ->
                view.onBrandsLoaded(list)
            }
        } else {
            view.onBrandsLoaded(view.getSavedBrandsList())
        }
    }

    fun loadFeatureProducts() {
        view.onLoading(PRODUCT_LOADING)
        if (view.getSavedFeaturedList().isEmpty()) {
            EcommerceRepo.featuredProduct { status, list ->
                view.productsLoaded(list)
            }
        } else {
            view.productsLoaded(view.getSavedFeaturedList())
        }

    }

    fun loadOnSaleProducts() {
        view.onLoading(PRODUCT_LOADING)
        if (view.getSavedOnSaleList().isEmpty()) {
            EcommerceRepo.onSale { status, list ->
                view.productsLoaded(list)
            }
        } else {
            view.productsLoaded(view.getSavedOnSaleList())
        }
    }

    fun loadTopRatedProducts() {
        view.onLoading(PRODUCT_LOADING)
        if (view.getSavedTopRatedList().isEmpty()) {
            EcommerceRepo.topRated { status, list ->
                view.productsLoaded(list)
            }
        } else {
            view.productsLoaded(view.getSavedTopRatedList())
        }
    }

    fun loadMostLikedProducts() {
        view.onLoading(PRODUCT_LOADING)
        if (view.getSavedMostLikedList().isEmpty()) {
            EcommerceRepo.mostLiked { status, list ->
                view.productsLoaded(list)
            }
        } else {
            view.productsLoaded(view.getSavedMostLikedList())
        }
    }

    fun loadRecommendedProducts() {
        view.onLoading(RECOMMENDED_LOADING)
        if (view.getSavedRecommendedList().isEmpty()) {
            EcommerceRepo.recommendedProduct { status, list ->
                view.onRecommendedLoaded(list)
            }
        } else {
            view.onRecommendedLoaded(view.getSavedRecommendedList())
        }

    }


}