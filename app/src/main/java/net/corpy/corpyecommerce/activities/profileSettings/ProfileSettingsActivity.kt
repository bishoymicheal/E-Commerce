package net.corpy.corpyecommerce.activities.profileSettings

import android.arch.lifecycle.Observer
import android.content.Intent
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.ToolbarInit
import kotlinx.android.synthetic.main.account_screen.*
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.ConnectionLiveData
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.noNetwork.NoNetworkActivity
import net.corpy.corpyecommerce.activities.profile.MyProfileActivity

class ProfileSettingsActivity : BaseActivity<ProfileSettingsPresenter>(), ProfileSettingsView, ToolbarInit {
    override fun initPresenter(): ProfileSettingsPresenter {
        return ProfileSettingsPresenter(this)
    }

    override fun initViews() {
        my_profile_text?.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))


        }
        language?.setOnClickListener {
            Utils.showLanguageDialog(this) {

            }
        }

        if (App.getCurrentLanguageKey(this) == "ar") current_lang.text = "العربية"
        else {
            current_lang.text = "ENGLISH"
        }


        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (isConnected == false)
                startActivity(Intent(this, NoNetworkActivity::class.java))
        })

    }

    override fun loadResourceLayout(): Int {
        return R.layout.account_screen
    }

    override fun onMenuItemClicked(itemId: Int) {
    }

    override fun showMessage(msg: String) {
    }

    override fun finishLoad() {
    }

    override fun onLoading(msg: String) {
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
        return getString(R.string.profile_settings)
    }

    override fun onToolbarClicked() {
        finish()
    }

    override fun showHomeUpButton(): Boolean {
        return true
    }
}