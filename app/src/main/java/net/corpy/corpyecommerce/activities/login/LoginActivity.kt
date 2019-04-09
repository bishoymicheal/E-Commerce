package net.corpy.corpyecommerce.activities.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.design.widget.Snackbar
import android.view.View
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.MenuInit
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.signup_layout.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.activities.profileSettings.ProfileSettingsActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView, ToolbarInit, MenuInit {
    override fun onSignupResult(status: Boolean, msg: String) {
        if (status) {
            Utils.showSuccessDialog(this, getString(R.string.account_created), msg) {
                navigateToLogin()
            }
        } else {
            Utils.showErrorDialog(this, getString(R.string.oops), msg, cancellable = true) {
                presenter.doSignUp(signup_create_button,
                        first_name_et, last_name_et, email_et, password_et, confirm_password_et)

            }
        }
    }


    override fun getToolbarId(): Int {
        return R.id.toolbar
    }

    override fun getToolbarTextColor(): Int {
        return R.color.toolbarTextColor
    }

    override fun navigateToLogin() {
        login_view?.visibility = View.VISIBLE
        signup_view?.visibility = View.GONE
    }

    override fun navigateToRegistration() {
        signup_view?.visibility = View.VISIBLE
        login_view?.visibility = View.GONE
    }


    override fun onLoginResult(status: Boolean, msg: String) {
        if (status) {
            Utils.showSuccessDialog(this, getString(R.string.you_are_in), msg) {
                finish()
            }
        } else {
            Utils.showErrorDialog(this, getString(R.string.oops), msg, cancellable =true) {}
        }
    }

    override fun finishActivity() {
        finish()
        overridePendingTransition(0, 0)
    }

    override fun initToolbarTitle(): String {
        return getString(R.string.login)
    }

    override fun onToolbarClicked() {
        if (presenter.isRegistrationLayout()) {
            presenter.navigateToLoginClicked()
        } else
            finishActivity()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }

    override fun initMenuRes(): Int {
        return R.menu.product_details_menu
    }

    override fun loadResourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun initViews() {

        if (App.instance.userResponse != null) {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
            finishActivity()
        }

        login_button?.setOnClickListener { presenter.doLogin(save_me, login_button, login_email_et, login_password_et) }
        create_account_button?.setOnClickListener { presenter.navigateToRegistrationClicked() }
        signup_create_button?.setOnClickListener {
            presenter.doSignUp(signup_create_button,
                    first_name_et, last_name_et, email_et, password_et, confirm_password_et)
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
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onLoading(msg: String) {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun finishLoad() {
        progress_bar?.visibility = View.GONE
    }

    override fun onLoadingFailed(msg: String) {}

}