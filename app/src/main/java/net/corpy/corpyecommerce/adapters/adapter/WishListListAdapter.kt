package net.corpy.corpyecommerce.adapters.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.adapters.bases.BaseAdapter
import net.corpy.corpyecommerce.adapters.bases.BaseViewHolder
import net.corpy.corpyecommerce.adapters.viewHolders.WishListViewHolder
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Wishlist

class WishListListAdapter(private var itemClickListener: ItemClickListener) :
        BaseAdapter<Wishlist, ItemClickListener>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Wishlist>() {
            override fun areItemsTheSame(oldItem: Wishlist, newItem: Wishlist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Wishlist, newItem: Wishlist): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun getListener(): ItemClickListener? {
        return itemClickListener
    }

    override fun getCreatedViewHolder(viewGroup: ViewGroup): BaseViewHolder<Wishlist, ItemClickListener> {
        val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.wishlist_product_layout_item, viewGroup, false)
        return WishListViewHolder(itemView)
    }


/*
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WishListViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.wishlist_product_layout_item, p0, false)
        return WishListViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: WishListViewHolder, p1: Int) {
        p0.resetItem()
        p0.bind(getItem(p1),itemClickListener)
    }

    fun getItemAt(position: Int): Wishlist {
        return getItem(position)
    }*/

}