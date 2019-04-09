package net.corpy.corpyecommerce.activities.noNetwork

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.no_network_layout.*
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R

class NoNetworkActivity : BaseActivity<NoNetworkPresenter>(), NoNetworkView, ToolbarInit {
    override fun finishLoad() {


    }

    override fun onLoading(msg: String) {
    }

    override fun onLoadingFailed(msg: String) {
    }

    override fun initPresenter(): NoNetworkPresenter {
        return NoNetworkPresenter(this)
    }

    override fun initViews() {

        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->

            if (isConnected == true) finish()

        })



        lyt_no_connection?.setOnClickListener {
            progress_bar?.visibility = View.VISIBLE
            lyt_no_connection?.visibility = View.GONE

            val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork?.isConnected == true)
                finish()
            else {
                progress_bar?.visibility = View.GONE
                lyt_no_connection?.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected == true)
            super.onBackPressed()
    }

    override fun loadResourceLayout(): Int {
        return R.layout.no_network_layout
    }

    override fun onMenuItemClicked(itemId: Int) {

    }

    override fun showMessage(msg: String) {

    }

    override fun getToolbarId(): Int {
        return R.id.toolbar
    }

    override fun getToolbarTextColor(): Int {
        return R.color.toolbarTextColor
    }

    override fun initToolbarTitle(): String {
        return getString(R.string.no_network_connection)
    }

    override fun onToolbarClicked() {

    }

    override fun showHomeUpButton(): Boolean {
        return false
    }
}