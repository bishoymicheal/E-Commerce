package net.corpy.corpyecommerce.interfaces

@FunctionalInterface
interface cardItemClickListener {
    fun onAdd(position: Int)
    fun onRemove(position: Int)
}