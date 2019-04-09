package net.corpy.corpyecommerce

import BaseResponse
import android.app.Activity
import android.content.*
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.view.inputmethod.InputMethodManager
import net.corpy.corpyecommerce.interfaces.CartListener


class App : MultiDexApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    var userResponse: BaseResponse.BaseResponse? = null
        set(value) {
            value?.data?.user?.api_token = value?.data?.api_token
            field = value
        }


    companion object {
        var cartListener: CartListener? = null
        var cartCount: Int = 0
        var cartUpTodate = false
        var savedUpTodate = false
        var wishlistCountChanged = false
        var cartCountChanged = false

        lateinit var instance: App

        fun hideKeyboard(activity: Activity) {
            try {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            } catch (e: Exception) {

            }

        }

        fun getCurrentLanguageKey(context: Context): String {
            val current = context.resources.configuration.locale
            return current.language
        }

        fun updateCartCount(count: Int) {
            cartListener?.cartCountChanged(count)
        }

    }

    fun registerCartListener(cartListener: CartListener) {
        App.cartListener = cartListener
    }

    fun unRegisterCartListener() {
        App.cartListener = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this, ""))

        Utils.setAppLanguage(this, Utils.getCurrentLanguage(this)) {}
    }

}

