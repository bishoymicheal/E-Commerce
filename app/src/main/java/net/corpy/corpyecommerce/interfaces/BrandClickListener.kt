package net.corpy.corpyecommerce.interfaces

import net.corpy.corpyecommerce.model.SubCategories

@FunctionalInterface
interface BrandClickListener {
    fun onBrandClick(brand: SubCategories)
}