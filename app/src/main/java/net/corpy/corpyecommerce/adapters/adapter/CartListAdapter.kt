package net.corpy.corpyecommerce.adapters.adapter

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.adapters.bases.BaseAdapter
import net.corpy.corpyecommerce.adapters.bases.BaseViewHolder
import net.corpy.corpyecommerce.adapters.viewHolders.CartViewHolder
import net.corpy.corpyecommerce.interfaces.ItemClickListener
import net.corpy.corpyecommerce.model.Cart

class CartListAdapter(private val itemClickListener: ItemClickListener) :
        BaseAdapter<Cart, ItemClickListener>(CartListAdapter.DIFF_CALLBACK) {
    override fun getCreatedViewHolder(viewGroup: ViewGroup): BaseViewHolder<Cart, ItemClickListener> {
        return CartViewHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_layout_item, viewGroup, false))
    }

    override fun getListener(): ItemClickListener? {
        return itemClickListener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
        }
    }


}