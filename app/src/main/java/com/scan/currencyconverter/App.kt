package com.scan.currencyconverter

import android.app.Application
import com.scan.currencyconverter.di.component.AppComponent
import com.scan.currencyconverter.di.component.DaggerAppComponent.builder

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        component = builder().build()
    }

    companion object {
        private lateinit var component: AppComponent
        fun getComponent(): AppComponent {
            return component
        }
    }


}