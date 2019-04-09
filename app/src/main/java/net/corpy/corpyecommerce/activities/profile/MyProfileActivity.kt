package net.corpy.corpyecommerce.activities.profile

import android.arch.lifecycle.Observer
import android.content.Intent
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.MenuInit
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.my_profile_activity.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.network.requestModel.UpdateUserRequest

class MyProfileActivity : BaseActivity<MyProfilePresenter>(), MyProfileView, ToolbarInit, MenuInit {
    override fun onLoadingFailed(msg: String) {

    }

    override fun getToolbarId(): Int {
        return R.id.toolbar
    }

    override fun getToolbarTextColor(): Int {
        return R.color.toolbarTextColor
    }

    override fun initToolbarTitle(): String {
        return getString(R.string.my_profile)
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }

    override fun initMenuRes(): Int {
        return R.menu.product_details_menu
    }

    override fun loadResourceLayout(): Int {
        return R.layout.my_profile_activity
    }

    override fun initPresenter(): MyProfilePresenter {
        return MyProfilePresenter(this)
    }

    override fun initViews() {
        val user = App.instance.userResponse?.data?.user
        if (user == null) {
            Utils.showErrorDialog(getCurrentContext()
                    , getCurrentContext()
                    .getString(R.string.oops)
                    , getString(R.string.error_happened), cancellable =false) { finish() }

        }

        first_name_et?.setText(user?.name ?: "User Name")

        email_et?.setText(user?.email)
        val updateuser = UpdateUserRequest()

        val povider = App.instance.userResponse?.data?.user?.provider ?: ""
        if (povider == "facebook") {
            login_lable?.visibility = View.GONE
            current_password_layout?.visibility = View.GONE
            new_password_layout?.visibility = View.GONE
        }

        signup_create_button?.setOnClickListener {
            updateuser.userName = first_name_et.text.toString()
            updateuser.email = email_et.text.toString()
            updateuser.password = new_password.text.toString()
            updateuser.current_password = current_password.text.toString()
            updateuser.token = App.instance.userResponse?.data?.user?.api_token ?: ""
            updateuser.current_email = App.instance.userResponse?.data?.user?.email
            presenter.updateUserData(updateuser)
        }


        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }

    override fun onMenuItemClicked(itemId: Int) {

    }

    override fun showMessage(msg: String) {

    }

    override fun onLoading(msg: String) {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun finishLoad() {
        progress_bar?.visibility = View.GONE
    }
}