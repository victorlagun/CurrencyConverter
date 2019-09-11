package com.scan.currencyconverter.di.component

import com.scan.currencyconverter.converter.CurrencyConverterPresenter
import com.scan.currencyconverter.di.module.AppModule
import com.scan.currencyconverter.di.module.RemoteModule
import com.scan.currencyconverter.di.module.RepositoryModule
import com.scan.currencyconverter.di.module.UtilsModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilsModule::class, RemoteModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(presenter: CurrencyConverterPresenter)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(appModule: AppModule)
    }
}