package net.corpy.corpyecommerce.activities.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hazem.mvpbase.BasePresenter
import com.hazem.utilslib.libs.UtilsFunctions
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.database.EcommerceDatabase
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.RegisterRequest
import net.corpy.corpyecommerce.network.requestModel.SocialRequest

class LoginPresenter(view: LoginView) : BasePresenter<LoginView>(view) {

    private var isRegisterLayout: Boolean = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCallbackManager: CallbackManager

    override fun resume() {
    }

    override fun pause() {
    }

    override fun create(savedInstance: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()
        val loginButton = view.getCurrentActivity().findViewById(R.id.fb_login_button) as LoginButton
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                view.onLoading("")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Utils.showErrorDialog(context
                        , context.getString(R.string.oops)
                        , error.message ?: context.getString(R.string.error_happend), cancellable =true) {}
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(view.getCurrentActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        user?.let {
                            doSocialLogin(user)
                        }
                    } else {
                        Utils.showErrorDialog(context
                                , context
                                .getString(R.string.oops)
                                , context.getString(R.string.auth_failed), cancellable =true) {}
                    }
                }
    }


    override fun destroy() {
    }

    override fun start() {
    }

    override fun stop() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun isRegistrationLayout(): Boolean {
        return isRegisterLayout
    }

    fun navigateToLoginClicked() {
        isRegisterLayout = false
        view.navigateToLogin()
    }

    fun navigateToRegistrationClicked() {
        isRegisterLayout = true
        view.navigateToRegistration()
    }

    fun doLogin(save_me: CheckBox, login_btn: Button,
                login_email_et: EditText, login_password_et: EditText) {

        App.hideKeyboard(view.getCurrentActivity())
        if (!UtilsFunctions.validateETNotEmpty(login_email_et, context.getString(R.string.emptyEditText))) return
        if (!UtilsFunctions.validateETNotEmpty(login_password_et, context.getString(R.string.emptyEditText))) return
        if (!UtilsFunctions.isValidEmail(login_email_et, context.getString(R.string.invalid_email))) return

        view.onLoading(context.getString(R.string.sign_in))
        login_btn.isEnabled = false

        val lang = App.getCurrentLanguageKey(context)

        EcommerceRepo.login(login_email_et.text.toString(), login_password_et.text.toString(), lang) { status, msg ->
            login_btn.isEnabled = true
            if (save_me.isChecked) {
                val r = Runnable {
                    if (App.instance.userResponse?.data?.user != null)
                        EcommerceDatabase.getAppDataBase(context = context)
                                ?.userDao()?.insertUser(App.instance.userResponse?.data?.user!!)
                    view.getCurrentActivity().runOnUiThread {
                        view.finishLoad()
                        view.onLoginResult(status, msg)
                    }
                }
                Thread(r).start()
            } else {
                view.finishLoad()
                view.onLoginResult(status, msg)
            }
        }

    }

    private fun doSocialLogin(user: FirebaseUser) {
        user.let {
            val socialRequest = SocialRequest()
            socialRequest.name = it.displayName
            socialRequest.email = it.email
            socialRequest.provider = "facebook"

            socialRequest.lang = App.getCurrentLanguageKey(context)

            view.onLoading(context.getString(R.string.sign_in))

            EcommerceRepo.socialLogin(socialRequest) { status, msg ->

                val r = Runnable {
                    if (App.instance.userResponse?.data?.user != null)
                        EcommerceDatabase.getAppDataBase(context)
                                ?.userDao()?.insertUser(App.instance.userResponse?.data?.user!!)
                    view.getCurrentActivity().runOnUiThread {
                        view.finishLoad()
                        view.onLoginResult(status, msg)
                    }
                }
                Thread(r).start()
            }
        }

    }


    fun doSignUp(signup_btn: Button, first_name_et: EditText, last_name_et: EditText, email_et: EditText,
                 password_et: EditText, confirm_password_et: EditText) {
        App.hideKeyboard(view.getCurrentActivity())
        if (!UtilsFunctions.validateETNotEmpty(first_name_et, context.getString(R.string.emptyEditText))) return
        if (!UtilsFunctions.validateETNotEmpty(last_name_et, context.getString(R.string.emptyEditText))) return
        if (!UtilsFunctions.validateETNotEmpty(email_et, context.getString(R.string.emptyEditText))) return
        if (!UtilsFunctions.isValidEmail(email_et, context.getString(R.string.invalid_email))) return
        if (!UtilsFunctions.validateETNotEmpty(password_et, context.getString(R.string.emptyEditText))) return
        if (!UtilsFunctions.validateETNotEmpty(confirm_password_et, context.getString(R.string.emptyEditText))) return

        if (password_et.text.toString() != confirm_password_et.text.toString()) {
            confirm_password_et.error = context.getString(R.string.password_confirmation_error)
            return
        }


        view.onLoading(context.getString(R.string.siging_up_msg))
        signup_btn.isEnabled = false

        val name = "${first_name_et.text} ${last_name_et.text}"

        val registerRequest = RegisterRequest()
        registerRequest.email = email_et.text.toString()
        registerRequest.password = password_et.text.toString()
        registerRequest.userName = name

        registerRequest.lang = App.getCurrentLanguageKey(context)

        EcommerceRepo.signUp(registerRequest) { status, msg ->
            signup_btn.isEnabled = true
            view.finishLoad()
            view.onSignupResult(status, msg)
        }
    }


}
