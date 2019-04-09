package net.corpy.corpyecommerce.activities.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.AppComponentFactory
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.MenuInit
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.login.LoginActivity
import net.corpy.corpyecommerce.activities.myorders.MyOrdersActivity
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.activities.productDetail.ProductDetailsActivity
import net.corpy.corpyecommerce.activities.search.SearchActivity
import net.corpy.corpyecommerce.adapters.ExpandableRecyclerAdapter
import net.corpy.corpyecommerce.adapters.ViewPagerAdapter
import net.corpy.corpyecommerce.interfaces.CartListener
import net.corpy.corpyecommerce.model.ParentCategories
import net.corpy.corpyecommerce.widget.BadgeView


class HomeActivity : BaseActivity<HomePresenter>(), HomeView, ToolbarInit, MenuInit, CartListener {
    override fun cartCountChanged(number: Int) {
        val tab = tabs?.getTabAt(2)
        val view: View? = tab?.customView
        val badgeView: BadgeView? = view?.findViewById(R.id.cart_count)

        badgeView?.text = number.toString()
        ProductDetailsActivity.cartCount?.text = number.toString()
        if (number == 0) {
            badgeView?.visibility = View.GONE
            ProductDetailsActivity.cartCount?.visibility = View.GONE
        } else {
            badgeView?.visibility = View.VISIBLE
            ProductDetailsActivity.cartCount?.visibility = View.VISIBLE
        }

    }

    override fun onMainCategoryLoaded(list: List<ParentCategories>) {
        menu_swipe.isRefreshing = false
        no_item_layout?.visibility = View.GONE
        mainCategoryAdapter?.products = list
        mainCategoryAdapter?.notifyDataSetChanged()

        if (list.isEmpty()) {
            no_item_layout?.visibility = View.VISIBLE
        }

    }


    override fun onLoadingFailed(msg: String) {

    }

    override fun getToolbarId(): Int {
        return R.id.toolbar
    }

    override fun getToolbarTextColor(): Int {
        return R.color.toolbarTextColor
    }

    override fun initToolbarTitle(): String {
        return getString(R.string.app_name)
    }

    override fun onToolbarClicked() {
    }

    override fun showHomeUpButton(): Boolean {
        return false
    }

    override fun initMenuRes(): Int {
        return R.menu.menu
    }

    override fun loadResourceLayout(): Int {
        return R.layout.activity_home
    }


    companion object {
        var goToCart: Boolean = false
    }


    override fun initPresenter(): HomePresenter {
        return HomePresenter(this)
    }

    override fun initViews() {

        App.instance.registerCartListener(this)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout?.addDrawerListener(toggle)
        toggle.syncState()

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager?.adapter = viewPagerAdapter
        viewPager?.offscreenPageLimit = 3

        tabs?.setupWithViewPager(viewPager)

        for (tab in 0..tabs.tabCount) {
            val view: View = when (tabs.getTabAt(tab)?.position) {
                0 -> LayoutInflater.from(this@HomeActivity).inflate(R.layout.hame_tap_layout, null)

                1 -> LayoutInflater.from(this@HomeActivity).inflate(R.layout.saved_tap_layout, null)

                2 -> LayoutInflater.from(this@HomeActivity).inflate(R.layout.cart_tap_layout, null)
                else -> {
                    LayoutInflater.from(this@HomeActivity).inflate(R.layout.hame_tap_layout, null)
                }
            }
            tabs.getTabAt(tab)?.customView = view
        }

        menu_swipe.setOnRefreshListener {
            presenter.loadMainProudactData()
        }

        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }


    var mainCategoryAdapter: ExpandableRecyclerAdapter? = null
    var mainCategouryList = ArrayList<ParentCategories>()
    private fun loadMenuCategories() {
        menu_recycler?.layoutManager = LinearLayoutManager(this)
        menu_recycler?.setHasFixedSize(true)
        mainCategoryAdapter = ExpandableRecyclerAdapter(mainCategouryList)
        menu_recycler?.adapter = mainCategoryAdapter
        presenter.loadMainProudactData()
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
        Utils.loadCartCount()
        loadMenuCategories()

        if (goToCart) {
            goToCart = false
            tabs?.getTabAt(2)?.select()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        if (App.instance.userResponse != null) {
            val menuItem = menu.findItem(R.id.Login)
            if (menuItem != null) menuItem.isVisible = false
        } else {
            val menuItem = menu.findItem(R.id.sign_out)
            if (menuItem != null) menuItem.isVisible = false
        }
        return true
    }

    override fun onMenuItemClicked(itemId: Int) {
        when (itemId) {
            R.id.search -> startActivity(Intent(this, SearchActivity::class.java))
            R.id.user -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.my_orders -> startActivity(Intent(this, MyOrdersActivity::class.java))
            R.id.Login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.sign_out -> {
                onLoading("")
                showMessage(getString(R.string.logging_out))
                Utils.signOut(this) {}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        drawer_layout?.closeDrawer(GravityCompat.START)
    }

    override fun showMessage(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onLoading(msg: String) {
//        progress_bar.visibility = View.VISIBLE
    }

    override fun finishLoad() {
//        progress_bar.visibility = View.GONE
    }
}