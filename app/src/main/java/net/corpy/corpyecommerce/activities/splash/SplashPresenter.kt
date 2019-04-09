package net.corpy.corpyecommerce.activities.splash

import BaseResponse
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.util.Log
import android.view.View
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.Constants
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.database.EcommerceDatabase
import net.corpy.corpyecommerce.model.Data
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*
import android.support.v4.content.ContextCompat.startActivity


class SplashPresenter(view: SplashView) : BasePresenter<SplashView>(view) {

    override fun resume() {
    }

    override fun pause() {
    }

    override fun create(savedInstance: Bundle?) {
        Utils.setAppLanguage(context, Utils.getCurrentLanguage(context)) {}
    }

    private fun setSplashTime(bottomSheetBehavior: BottomSheetBehavior<View>, time: Long) {
        Handler().postDelayed({
            if (isFirstLaunch(context)) {
                Utils.showLanguageSheet(context, bottomSheetBehavior) {
                    view.navigateToMain()
                }
            } else {
                view.navigateToMain()
            }
        }, time)
    }


    private val INSTALLATION = "INSTALLATION"

    @Synchronized
    fun isFirstLaunch(context: Context): Boolean {
        var sID: String? = null
        var launchFlag = false
        if (sID == null) {
            val installation = File(context.filesDir, INSTALLATION)
            try {
                if (!installation.exists()) {
                    launchFlag = true
                    writeInstallationFile(installation)
                }
                sID = readInstallationFile(installation)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
        return launchFlag
    }

    @Throws(IOException::class)
    private fun readInstallationFile(installation: File): String {
        val f = RandomAccessFile(installation, "r")// read only mode
        val bytes = ByteArray(f.length().toInt())
        f.readFully(bytes)
        f.close()

        return String(bytes)
    }

    @Throws(IOException::class)
    private fun writeInstallationFile(installation: File) {
        val out = FileOutputStream(installation)
        val id = UUID.randomUUID().toString()
        out.write(id.toByteArray())
        out.close()
    }

    override fun destroy() {
    }


    override fun start() {
        checkLoggedUser()
    }

    private fun checkLoggedUser() {
        val r = Runnable {
            val user = EcommerceDatabase.getAppDataBase(context = context)?.userDao()?.getCurrentUser()
            if (user != null) {
                val data = Data(user, user.api_token)
                val u = BaseResponse.BaseResponse(true, null, data)
                App.instance.userResponse = u
            }
        }
        Thread(r).start()
    }

    override fun stop() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> {
                view.getCurrentActivity().finish()
/*
                val LINK_TO_GOOGLE_PLAY_SERVICES = "play.google.com/store/apps/details?id=com.google.android.gms&hl=en"
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://$LINK_TO_GOOGLE_PLAY_SERVICES")))
                    view.getCurrentActivity().finish()
                } catch (anfe: android.content.ActivityNotFoundException) {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://$LINK_TO_GOOGLE_PLAY_SERVICES")))
                    view.getCurrentActivity().finish()
                }*/
            }
        }
    }

    fun showLangDialog(bottomSheet: View) {
        upgradeSecurityProvider(context, bottomSheet)
    }

    fun upgradeSecurityProvider(context: Context, bottomSheet: View) {
        try {
            ProviderInstaller.installIfNeededAsync(context, object : ProviderInstaller.ProviderInstallListener {
                override fun onProviderInstalled() {

                    val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

                    setSplashTime(bottomSheetBehavior, Constants.SPLASH_DISPLAY_LENGTH.toLong())

                    Log.e("SSLFix", "New security provider installed.")
                }

                override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent) {
                    // Prompt the user to install/update/enable Google Play services.
                    GoogleApiAvailability.getInstance()
                            .showErrorNotification(context, errorCode)
                    GoogleApiAvailability.getInstance().showErrorDialogFragment(view.getCurrentActivity(), errorCode, 1) {
                        view.getCurrentActivity().finish()
                    }
                    Log.e("SSLFix", "New security provider install failed.")
                }
            })
        } catch (ex: Exception) {
            Log.e("SSLFix", "Unknown issue trying to install a new security provider", ex)
        }


    }
}