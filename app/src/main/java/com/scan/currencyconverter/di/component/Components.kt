package com.scan.currencyconverter.di.component

import com.scan.currencyconverter.converter.CurrencyConverterActivity
import com.scan.currencyconverter.converter.CurrencyConverterPresenter
import com.scan.currencyconverter.di.module.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ConverterModule::class, AppModule::class, UtilsModule::class, RemoteModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(presenter: CurrencyConverterPresenter)
    fun inject(view: CurrencyConverterActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(appModule: AppModule)
    }
}