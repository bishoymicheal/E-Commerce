package net.corpy.corpyecommerce

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.view.View


fun navigateToActivity(context: Context, intent: Intent) {
    navigateToActivityWithTransaction(context, intent, null)
}

fun navigateToActivityWithTransaction(activity: Activity, intent: Intent, sharedView: View?) {
//    if (sharedView == null)
    navigateToActivity(activity, intent)
//    else
//        navigateToActivityWithTransaction(activity, intent, createActivityTransitionOption(activity, sharedView))
}

fun navigateToActivityWithTransaction(activity: Activity, intent: Intent, sharedView: View, transitionName: String) {
    navigateToActivityWithTransaction(activity, intent, createActivityTransitionOption(activity, sharedView, transitionName))
}

fun navigateToActivityWithTransaction(context: Context, intent: Intent, activityOptionsCompat: ActivityOptionsCompat?) {
    if (activityOptionsCompat == null)
        context.startActivity(intent)
    else
        context.startActivity(intent, activityOptionsCompat.toBundle())
}

fun getTransitionNameFromView(view: View) = ViewCompat.getTransitionName(view)

fun createActivityTransitionOption(activity: Activity, sharedView: View, transitionName: String): ActivityOptionsCompat {
    return ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, transitionName)
}

fun createActivityTransitionOption(activity: Activity, sharedView: View): ActivityOptionsCompat {
    val transitionName = getTransitionNameFromView(sharedView)
            ?: throw IllegalArgumentException("given view must have android:transitionName property")
    return ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, transitionName)
}
