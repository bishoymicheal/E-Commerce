package net.corpy.corpyecommerce.adapters.bases

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup

abstract class BaseAdapter<T, L>(callbackDiff: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, BaseViewHolder<T, L>>(callbackDiff) {


    abstract fun getCreatedViewHolder(viewGroup: ViewGroup): BaseViewHolder<T, L>
    abstract fun getListener(): L?

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder<T, L> {
        return getCreatedViewHolder(p0)
    }

    override fun onBindViewHolder(p0: BaseViewHolder<T, L>, p1: Int) {
        p0.resetItem()
        p0.bind(getItem(p1), getListener())
    }

    fun getItemAt(position: Int): T {
        return getItem(position)
    }
}