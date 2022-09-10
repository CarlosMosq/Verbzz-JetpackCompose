package com.company.verbzz_app.view_models

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdViewModel : ViewModel() {
    private var mInterstitialAd: InterstitialAd? = null

    fun loadInterstitialAd(context: Context, navController: NavController, route: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            context.getString(R.string.test_Interstitial_Ad_ID),
            adRequest,
            object: InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback =
                        object: FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            mInterstitialAd = null
                            navController.navigate(route = route)
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    mInterstitialAd = null
                }
            }
        )
    }

    fun showAd(activity: Activity) {
        mInterstitialAd?.show(activity) ?:
        Log.d("AD_SHOW_ERROR", "The interstitial ad wasn't ready yet.")
    }
}