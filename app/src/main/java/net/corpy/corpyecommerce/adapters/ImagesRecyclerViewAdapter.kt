package net.corpy.corpyecommerce.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.interfaces.ItemClickListener

class ImagesRecyclerViewAdapter(var products: List<String>,
                                var itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder>() {
    var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.product_images_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setSelectedPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]
        if (position == currentPosition)
            holder.imageView.background = holder.itemView.context.resources.getDrawable(R.drawable.boarder)
        else holder.imageView.background = holder.itemView.context.resources.getDrawable(R.drawable.defult_boarder)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
            setSelectedPosition(position)
        }

        Picasso.get().load(Constants.BASE_IMAGES_URL + product).error(R.drawable.error).fit().into(holder.imageView, object : Callback {
            override fun onError(e: Exception?) {
                holder.progress_bar.visibility = View.GONE
            }

            override fun onSuccess() {
                holder.progress_bar.visibility = View.GONE
            }
        })

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById(R.id.ad_image) as ImageView
        val progress_bar = itemView.findViewById(R.id.progress_bar) as ProgressBar

    }


}