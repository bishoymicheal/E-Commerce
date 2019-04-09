package net.corpy.corpyecommerce.activities.profile

import android.content.Intent
import android.os.Bundle
import com.hazem.mvpbase.BasePresenter
import net.corpy.corpyecommerce.App
import net.corpy.corpyecommerce.R
import net.corpy.corpyecommerce.Utils
import net.corpy.corpyecommerce.activities.login.LoginActivity
import net.corpy.corpyecommerce.database.EcommerceDatabase
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.UpdateUserRequest

class MyProfilePresenter(view: MyProfileView) : BasePresenter<MyProfileView>(view) {
    override fun resume() {

    }

    override fun pause() {

    }

    override fun create(savedInstance: Bundle?) {

    }

    override fun destroy() {

    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    fun updateUserData(updateuser: UpdateUserRequest) {
        view.onLoading("")
        val userId: String = App.instance.userResponse?.data?.user?.id?.toString() ?: "-1"

        EcommerceRepo.updateUser(userId, updateuser) { states, msg ->
            view.finishLoad()
            if (states) {

                val r = Runnable {
                    EcommerceDatabase.getAppDataBase(context = context)?.userDao()?.deleteAll()
                    view.getCurrentActivity().runOnUiThread {
                        Utils.showSuccessDialog(context, context.getString(R.string.you_are_out), msg) {
                            Utils.fbSignOut()
                            view.getCurrentActivity().finish()
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        }
                    }
                }
                Thread(r).start()


            } else {
                Utils.showErrorDialog(context, context.getString(R.string.oops), msg, cancellable =true) {}
            }

        }

    }

}