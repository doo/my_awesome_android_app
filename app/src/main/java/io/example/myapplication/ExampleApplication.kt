package io.example.myapplication

import android.app.Application
import io.scanbot.sdk.ScanbotSDKInitializer

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ScanbotSDKInitializer()
            .license(this, SCANBOT_SDK_LICENSE_KEY) // see below
            .initialize(this)
    }

    companion object {
        // TODO add the Scanbot SDK license key here.
        // Please note: The Scanbot SDK will run without a license key for one minute per session!
        // After the trial period is over all Scanbot SDK functions as well as the UI components will stop working
        // or may be terminated. You can get an unrestricted "no-strings-attached" 30 day trial license key for free.
        // Please submit the trial license form (https://scanbot.io/sdk/trial.html) on our website by using
        // the app identifier "com.example.myapplication" of this example app or of your app.
        const val SCANBOT_SDK_LICENSE_KEY = ""
    }
}