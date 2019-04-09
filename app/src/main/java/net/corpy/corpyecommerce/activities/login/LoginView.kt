package net.corpy.corpyecommerce.activities.login

import com.hazem.mvpbase.LoadingView

interface LoginView : LoadingView {
    fun navigateToLogin()
    fun navigateToRegistration()

    //    fun updateProfileUi(user: User)
    fun onLoginResult(status: Boolean, msg: String)

    fun onSignupResult(status: Boolean, msg: String)
    fun finishActivity()
}