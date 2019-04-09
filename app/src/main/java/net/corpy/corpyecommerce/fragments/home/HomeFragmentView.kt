package net.corpy.corpyecommerce.fragments.home

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Product
import net.corpy.corpyecommerce.model.Slider
import net.corpy.corpyecommerce.model.SubCategories

interface HomeFragmentView : LoadingView {
    fun onAdsLoaded(products: List<Slider>)
    fun productsLoaded(products: List<Product>)
    fun onBrandsLoaded(brands: List<SubCategories>)
    fun onBestSellerLoaded(product: List<Product>)

    fun loadFeatureProducts()
    fun loadOnSaleProducts()
    fun loadTopRatedProducts()
    fun loadMostLikedProducts()
    fun onRecommendedLoaded(list: List<Product>)


    fun getSavedAdsList(): List<Slider>
    fun getSavedBestSellerList(): List<Product>
    fun getSavedFeaturedList(): List<Product>
    fun getSavedOnSaleList(): List<Product>
    fun getSavedTopRatedList(): List<Product>
    fun getSavedMostLikedList(): List<Product>
    fun getSavedRecommendedList(): List<Product>
    fun getSavedBrandsList(): List<SubCategories>

}