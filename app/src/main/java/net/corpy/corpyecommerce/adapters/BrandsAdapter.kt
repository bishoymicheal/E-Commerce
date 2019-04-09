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
import net.corpy.corpyecommerce.interfaces.BrandClickListener
import net.corpy.corpyecommerce.model.SubCategories

class BrandsAdapter(var department: List<SubCategories>,
                    var brandClickListener: BrandClickListener) : RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.department_layout_item, parent, false))
    }

    override fun getItemCount(): Int {
        return department.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val department = department[position]

        holder.itemView.setOnClickListener { brandClickListener.onBrandClick(department) }

        Picasso.get().load(Constants.BASE_IMAGES_URL+department.image).error(R.drawable.error).fit().into(holder.departmentImage, object : Callback {
            override fun onError(e: Exception?) {
                holder.progressBar.visibility = View.GONE
            }

            override fun onSuccess() {
                holder.progressBar.visibility = View.GONE
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val departmentImage: ImageView = itemView.findViewById(R.id.department_image) as ImageView
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
    }


}