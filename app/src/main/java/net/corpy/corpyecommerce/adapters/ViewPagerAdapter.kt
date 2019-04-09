package net.corpy.corpyecommerce.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import net.corpy.corpyecommerce.fragments.cart.CartFragment
import net.corpy.corpyecommerce.fragments.home.HomeFragment
import net.corpy.corpyecommerce.fragments.saved.SavedProductsFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> SavedProductsFragment.newInstance()
            2 -> CartFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}