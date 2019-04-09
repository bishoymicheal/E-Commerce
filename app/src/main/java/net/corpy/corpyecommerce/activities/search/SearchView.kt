package net.corpy.corpyecommerce.activities.search

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.Product

interface SearchView:LoadingView {
    fun onSearchDataLoaded(list: List<Product>)
}
