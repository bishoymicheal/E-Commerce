package net.corpy.corpyecommerce.adapters

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.activities.products.ProductsActivity
import net.corpy.corpyecommerce.model.ParentCategories
import net.corpy.corpyecommerce.network.EcommerceRepo


class ExpandableRecyclerAdapter(var products: List<ParentCategories>)
    : RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int):
            ExpandableRecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_parent_child_listing, parent, false)
        return ExpandableRecyclerAdapter.ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ExpandableRecyclerAdapter.ViewHolder, position: Int) {
        val dummyParentDataItem = products[position]

        if (App.getCurrentLanguageKey(holder.context) == "ar")
            holder.productName.text = dummyParentDataItem.ar_name
        else {
            holder.productName.text = dummyParentDataItem.en_name
        }

        Picasso.get().load(Constants.BASE_IMAGES_URL + dummyParentDataItem.image).fit().into(holder.product_image)
//        Picasso.get().load(Constants.BASE_IMAGES_URL + dummyParentDataItem.image).fit().into(holder.bg_image)

        holder.parent_line.setOnClickListener {

            if (holder.subcategoriesLayout.visibility == View.VISIBLE) {
                holder.subcategoriesLayout.visibility = View.GONE
                holder.arrow_img.setImageDrawable(holder.itemView.context
                        .resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            } else {
                holder.subcategoriesLayout.visibility = View.VISIBLE
                holder.arrow_img.setImageDrawable(holder.itemView.context
                        .resources.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp))

                loadList(holder.itemView.context,
                        dummyParentDataItem,
                        holder.subcategoriesLayout,
                        holder.progress_bar)
            }

        }

    }

    private fun loadList(context: Context,
                         parent: ParentCategories,
                         linearLayout_childItems: LinearLayout,
                         progress_bar: ProgressBar) {
        progress_bar.visibility = View.VISIBLE
        linearLayout_childItems.removeAllViews()

        EcommerceRepo.getSubCategories(parent.id.toString(), parent.ar_name, parent.en_name) { status, list ->
            progress_bar.visibility = View.GONE
            list.forEach { categories ->

                val textView = TextView(context)

                if (App.getCurrentLanguageKey(context) == "ar")
                    textView.text = categories.ar_name
                else {
                    textView.text = categories.en_name
                }
                textView.setPadding(0, 20, 0, 20)
                textView.gravity = Gravity.CENTER
                textView.setTextColor(ContextCompat.getColor(context, R.color.toolbarTextColor))
                textView.background = ContextCompat.getDrawable(context, R.drawable.background_sub_module_text)
                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                textView.setOnClickListener {
                    context.startActivity(Intent(context, ProductsActivity::class.java)
                            .putExtra("id", categories.id.toString())
                            .putExtra("ar_name", categories.ar_name)
                            .putExtra("en_name", categories.en_name))
                }
                linearLayout_childItems.addView(textView, layoutParams)

            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setDataList(products: List<ParentCategories>?) {
        this.products = ArrayList()

        if (products == null || products.isEmpty()) {
            this.products = ArrayList()
        } else {
            this.products = products
        }
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val context: Context = itemView.context
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val subcategoriesLayout: LinearLayout = itemView.findViewById(R.id.ll_child_items)
        val arrow_img: ImageView = itemView.findViewById(R.id.arrow_img)
        val product_image: ImageView = itemView.findViewById(R.id.product_image)
        val bg_image: ImageView = itemView.findViewById(R.id.bg_image)
        val progress_bar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val parent_line: LinearLayout = itemView.findViewById(R.id.parent_line)


        override fun onClick(view: View) {
            if (view.id == R.id.parent_line) {
                if (subcategoriesLayout.visibility == View.VISIBLE) {
                    subcategoriesLayout.visibility = View.GONE
                    arrow_img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
                } else {
                    subcategoriesLayout.visibility = View.VISIBLE
                    arrow_img.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp))
                }
            } else {
                val textViewClicked = view as TextView
//                Toast.makeText(context, "" + textViewClicked.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        init {
            subcategoriesLayout.visibility = View.GONE
            (itemView.findViewById(R.id.parent_line) as LinearLayout).setOnClickListener(this)

        }

    }


}
