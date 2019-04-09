package net.corpy.corpyecommerce.adapters

import android.content.Context
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.model.Slider

class AdViewPagerAdapter(val context: Context,
                         private var products: List<Slider>) : PagerAdapter() {


    override fun getCount(): Int {
        return products.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = LayoutInflater.from(context).inflate(R.layout.ad_item_layout, view, false)
        val product = products[position]


        val imageView = imageLayout.findViewById(R.id.ad_image) as ImageView
        val ad_title = imageLayout.findViewById(R.id.ad_title) as TextView
        if (App.getCurrentLanguageKey(context) == "ar")
            ad_title.text = product.ar_title
        else {
            ad_title.text = product.en_title
        }
        val progress_bar = imageLayout.findViewById(R.id.progress_bar) as ProgressBar
        Picasso.get().load(Constants.BASE_IMAGES_URL + product.image).error(R.drawable.error).fit().into(imageView, object : Callback {
            override fun onError(e: Exception?) {
                progress_bar.visibility = View.GONE
            }

            override fun onSuccess() {
                progress_bar.visibility = View.GONE
            }
        })

        view.addView(imageLayout, 0)

        return imageLayout
    }

    fun setDataList(slider: List<Slider>?) {
        if (slider == null || slider.isEmpty()) {
            this.products = ArrayList()
        } else {
            this.products = (slider)
        }
        notifyDataSetChanged()
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }
}