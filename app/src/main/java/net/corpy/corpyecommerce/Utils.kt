package net.corpy.corpyecommerce

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.hazem.utilslib.libs.UtilsFunctions
import net.corpy.corpyecommerce.activities.home.HomeActivity
import net.corpy.corpyecommerce.activities.productDetail.ProductDetailsActivity
import net.corpy.corpyecommerce.database.EcommerceDatabase
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.widget.BadgeView
import net.corpy.corpyecommerce.widget.dialogLib.SweetAlertDialog
import java.util.*


object Utils {

    fun showErrorDialog(context: Context, title: String, msg: String, confirmText: String
    = context.getString(R.string.retry), cancellable: Boolean = false,
                        onClick: () -> Unit) {

        val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
        dialog.titleText = title
        dialog.contentText = msg
        dialog.setCancelable(cancellable)
        dialog.confirmText = context.getString(R.string.retry)
        dialog.setConfirmClickListener {
            onClick()
            dialog.dismissWithAnimation()
        }
        dialog.show()


        /*  val dialog = AlertDialog.Builder(context)
          val view = LayoutInflater.from(context).inflate(R.layout.error_dialog_message, null)
          val message = view.findViewById<TextView>(R.id.message)
          if (!msg.isEmpty())
              message.text = msg
          val button = view.findViewById<CardView>(R.id.button)
  //        val closeBtn = view.findViewById<ImageButton>(R.id.close_btn)
          dialog.setCancelable(cancellable)
          dialog.setView(view)
          val d = dialog.create()
          d?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
          button.setOnClickListener {
              onClick()
              d.dismiss()
          }
  //        closeBtn.setOnClickListener {
  //            d.dismiss()
  //        }
          d.setOnDismissListener { onDismiss() }
          d.show()*/
    }

    fun showSuccessDialog(context: Context, title: String, msg: String,
                          cancellable: Boolean = false, onClick: () -> Unit) {

        val dialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
        dialog.titleText = title
        dialog.contentText = msg
        dialog.setCancelable(cancellable)
        dialog.confirmText = context.getString(android.R.string.ok)
        dialog.setConfirmClickListener {
            onClick()
            dialog.dismissWithAnimation()
        }
        dialog.show()

        /*  val dialog = AlertDialog.Builder(context)
          val view = LayoutInflater.from(context).inflate(R.layout.dialog_message, null)
          dialog.setCancelable(cancellable)
          dialog.setView(view)
          val message = view.findViewById<TextView>(R.id.message)
          if (!msg.isEmpty())
              message.text = msg
          val button = view.findViewById<CardView>(R.id.button)
          val d = dialog.create()
          d?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
          button.setOnClickListener {
              onClick()
              d.dismiss()
          }
          d.show()*/
    }

    fun showWarningDialog(context: Context, title: String, msg: String,
                          cancellable: Boolean = false, onClick: () -> Unit) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
        dialog.titleText = title
        dialog.contentText = msg
        dialog.setCancelable(cancellable)
        dialog.cancelText = context.getString(android.R.string.cancel)
        dialog.showCancelButton(true)
        dialog.confirmText = context.getString(android.R.string.ok)
        dialog.setCancelClickListener { sDialog -> sDialog.cancel() }
        dialog.setConfirmClickListener {
            onClick()
            dialog.dismissWithAnimation()
        }
        dialog.show()

    }

    fun showLanguageSheet(context: Context, mBehavior: BottomSheetBehavior<View>, cancellable: Boolean = false, onComplete: () -> Unit) {
        if (mBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val view = LayoutInflater.from(context).inflate(R.layout.language_dialog, null)
        val english = view.findViewById<CardView>(R.id.english_card)
        val arabic = view.findViewById<CardView>(R.id.arabic_card)
        val mBottomSheetDialog = BottomSheetDialog(context)
        mBottomSheetDialog.setCancelable(cancellable)
        english.setOnClickListener {
            Utils.setAppLanguage(context, "en") {
                onComplete()
            }
            mBottomSheetDialog.dismiss()
        }
        arabic.setOnClickListener {
            Utils.setAppLanguage(context, "ar") {
                onComplete()
            }
            mBottomSheetDialog.dismiss()
        }
        mBottomSheetDialog.setContentView(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // set background transparent
        (view.parent as View).setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        mBottomSheetDialog.show()
    }

    fun showLanguageDialog(context: Context, cancellable: Boolean = false, onComplete: () -> Unit) {
        val dialog = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.language_dialog, null)
        dialog.setView(view)
        dialog.setCancelable(cancellable)
        dialog.create()
        val d = dialog.show()
        val linux_shared_web_hosting_card = view.findViewById<CardView>(R.id.english_card)
        val linux_reseller = view.findViewById<CardView>(R.id.arabic_card)
        linux_shared_web_hosting_card.setOnClickListener {
            setAppLanguage(context, "en") {
                onComplete()
            }
            d.dismiss()
        }
        linux_reseller.setOnClickListener {
            setAppLanguage(context, "ar") {
                onComplete()
            }
            d.dismiss()
        }
    }

    fun setAppLanguage(context: Context, languageCode: String, onComplete: () -> Unit) {
        try {
            val locale = Locale(languageCode)
            val lang = locale.language
            val current = context.resources.configuration.locale
            val LAng = current.language
            if (lang != LAng) {
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
                saveLanguage(context, languageCode)
                if (context is Activity) {
                    val i = context.baseContext.packageManager
                            .getLaunchIntentForPackage(context.baseContext.packageName)
                    i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(i)
                    context.overridePendingTransition(0, 0)
                }
            } else {
                onComplete()
            }
        } catch (e: Exception) {
        }
    }

    private fun saveLanguage(context: Context, languageCode: String) {
        val editor = context.getSharedPreferences(UtilsFunctions.PREFS_NAME, MODE_PRIVATE).edit()
        editor.putString(UtilsFunctions.PREF_LANGUAGE, languageCode)
        editor.apply()
    }

    fun getCurrentLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(UtilsFunctions.PREFS_NAME, MODE_PRIVATE)
        return prefs.getString(UtilsFunctions.PREF_LANGUAGE, "en") ?: "en"
    }


    fun signOut(activity: Activity, onComplete: (status: Boolean) -> Unit) {
        if (App.instance.userResponse != null && App.instance.userResponse?.data?.user?.id != null)
            EcommerceRepo.logoutUser(App.instance.userResponse?.data?.user?.id!!) { status, msg ->
                if (status) {
                    val r = Runnable {
                        if (App.instance.userResponse?.data?.user != null)
                            EcommerceDatabase.getAppDataBase(context = activity)
                                    ?.userDao()?.deleteAll()
                        activity.runOnUiThread {
                            App.instance.userResponse = null
                            FirebaseAuth.getInstance().signOut()
                            LoginManager.getInstance().logOut()
                            Utils.showSuccessDialog(activity, "", msg) {
                                activity.recreate()
                            }
                        }
                    }
                    Thread(r).start()
                } else {
                    onComplete(status)
                    Utils.showErrorDialog(activity, "Oops", "Error While Logout",
                            cancellable = true) { signOut(activity) {} }
                }
            }
    }

    fun fbSignOut() {
        App.instance.userResponse = null
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
    }

    fun loadCartCount() {
        if (App.cartUpTodate) {
            updateCartTabNumber(App.cartCount)
        } else {
            val id = App.instance.userResponse?.data?.user?.id?.toString().toString()
            EcommerceRepo.loadCart(id) { status, data ->
                if (status) {
                    App.cartUpTodate = true
                    App.cartCount = data?.cartCount ?: 0
                    updateCartTabNumber(App.cartCount)
                } else {
                    App.cartUpTodate = false
                }
            }
        }

    }


    fun updateCartTabNumber(number: Int) {
        App.updateCartCount(number)
    }

}