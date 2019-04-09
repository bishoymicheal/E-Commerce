package net.corpy.corpyecommerce.activities.home

import com.hazem.mvpbase.LoadingView
import net.corpy.corpyecommerce.model.ParentCategories

interface HomeView : LoadingView {
    fun onMainCategoryLoaded(list: List<ParentCategories>)
}