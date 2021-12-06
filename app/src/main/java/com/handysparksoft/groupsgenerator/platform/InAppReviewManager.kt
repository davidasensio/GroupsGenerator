package com.handysparksoft.groupsgenerator.platform

import android.app.Activity
import android.util.Log
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.model.ReviewErrorCode

class InAppReviewManager(private val reviewManager: ReviewManager) {
    fun requestReviewFlow(activity: Activity) {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                launchReviewFlow(activity, reviewInfo)
                Log.d(TAG, "addOnCompleteListener of requestReviewFlow successful")
            } else {
                @ReviewErrorCode
                val reviewErrorCode = (task.exception as ReviewException).errorCode
                Log.e(TAG, reviewErrorCode.toString())
            }
        }
    }

    private fun launchReviewFlow(activity: Activity, reviewInfo: ReviewInfo) {
        val flow = reviewManager.launchReviewFlow(activity, reviewInfo)
        flow.addOnCompleteListener { _ ->
            // The flow has finished.
            Log.d(TAG, "addOnCompleteListener of launchReviewFlow successful")
        }
    }

    companion object {
        private val TAG = InAppReviewManager::class.simpleName
    }
}
