package net.corpy.corpyecommerce.activities.splash

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.util.Base64
import android.util.Log
import android.view.WindowManager
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller
import com.hazem.mvpbase.BaseActivity
import com.hazem.mvpbase.LoadingView
import kotlinx.android.synthetic.main.activity_splash.*
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.activities.home.HomeActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class SplashActivity : BaseActivity<SplashPresenter>(), LoadingView, SplashView {
    override fun onLoadingFailed(msg: String) {

    }


    override fun navigateToMain() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
        overridePendingTransition(0, 0)
    }

    override fun loadResourceLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initPresenter(): SplashPresenter {
        return SplashPresenter(this)
    }

    private var mBehavior: BottomSheetBehavior<*>? = null
    private var mBottomSheetDialog: BottomSheetDialog? = null

    override fun initViews() {
     getFBHashCode()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
        }

//        presenter.upgradeSecurityProvider(this)

        presenter.showLangDialog(bottom_sheet)



    }


    private fun getFBHashCode() {
        try {
            val info = packageManager.getPackageInfo("net.corpy.corpyecommerce", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something)
            }
        } catch (el: PackageManager.NameNotFoundException) {
            Log.e("name not found", el.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }
    }

    override fun onMenuItemClicked(itemId: Int) {
    }

    override fun showMessage(msg: String) {
    }

    override fun onLoading(msg: String) {
    }

    override fun finishLoad() {
    }
}