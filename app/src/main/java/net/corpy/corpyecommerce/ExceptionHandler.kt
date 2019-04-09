package net.corpy.corpyecommerce

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log

import net.corpy.corpyecommerce.activities.splash.SplashActivity
import net.corpy.corpyecommerce.network.EcommerceRepo
import net.corpy.corpyecommerce.network.requestModel.ErrorSubmitRequest

import java.io.PrintWriter
import java.io.StringWriter

class ExceptionHandler(private val myContext: Context, private val className: String) : java.lang.Thread.UncaughtExceptionHandler {

    private val isUIThread: Boolean
        get() = Looper.getMainLooper().thread === Thread.currentThread()


    override fun uncaughtException(thread: Thread, exception: Throwable) {
        try {
            if (isUIThread) {
                handelUnCaughtException(exception)
            } else {  //handle non UI thread throw uncaught exception

                Handler(Looper.getMainLooper()).post { handelUnCaughtException(exception) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun handelUnCaughtException(exception: Throwable) {
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))
        //error to be send
        val LINE_SEPARATOR = "\n"
        val errorReport = "************ CAUSE OF ERROR ************\n\n" + stackTrace.toString() +
                "\n************ DEVICE INFORMATION ***********\n" +
                "Brand: " + Build.BRAND + LINE_SEPARATOR +
                "Device: " + Build.DEVICE + LINE_SEPARATOR +
                "Model: " + Build.MODEL + LINE_SEPARATOR +
                "Id: " + Build.ID + LINE_SEPARATOR +
                "Product: " + Build.PRODUCT + LINE_SEPARATOR +
                "\n************ FIRMWARE ************\n" +
                "SDK: " + Build.VERSION.SDK + LINE_SEPARATOR +
                "Release: " + Build.VERSION.RELEASE + LINE_SEPARATOR +
                "Incremental: " + Build.VERSION.INCREMENTAL + LINE_SEPARATOR +
                "\n*************^^^^^^^Our Implemintation^^^^^^**********\n" +
                "Error At Class " + className + LINE_SEPARATOR +
                "Error Cause " + (if (exception.cause == null) "" else exception.cause.toString()) + LINE_SEPARATOR +
                "Error Message " + (if (exception.message == null) "" else exception.message) + LINE_SEPARATOR

        Log.e("UnCaughtException", errorReport)

        val errorSubmitRequest = ErrorSubmitRequest()
        errorSubmitRequest.message = errorReport


        EcommerceRepo.submitError(errorSubmitRequest) { _, _ ->
            Utils.showErrorDialog(myContext, myContext.getString(R.string.oops),
                    myContext.getString(R.string.error_happend), "RESTART", cancellable = false) {
                val intent = Intent(myContext, SplashActivity::class.java)
                myContext.startActivity(intent)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(10)
            }
        }
    }

}