package net.corpy.corpyecommerce.adapters.bases

import android.support.v7.widget.RecyclerView
import android.view.View


abstract class BaseViewHolder<T, L>(itemView: View)
    : RecyclerView.ViewHolder(itemView) {
    internal abstract fun bind(item: T, listener: L?)
    internal abstract fun resetItem()
}