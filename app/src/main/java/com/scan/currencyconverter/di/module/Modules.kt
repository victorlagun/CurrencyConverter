package com.scan.currencyconverter.di.module

import android.content.Context
import androidx.annotation.NonNull
import com.scan.currencyconverter.model.CurrencyOfficialRate
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
import com.scan.currencyconverter.util.Formatter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @NonNull
    @Singleton
    fun provideConverter(rates: List<CurrencyOfficialRate>, fromCurrency: String): Converter {
        return Converter(rates, fromCurrency)
    }

    @Provides
    @NonNull
    fun provideFormatter(pattern: String): Formatter {
        return Formatter(pattern)
    }
}

@Module
class RepositoryModule {

    @Provides
    @NonNull
    @Singleton
    fun provideRepository(remote: Remote): Repository {
        return Repository(remote)
    }
}

@Module
class RemoteModule {

    @Provides
    @NonNull
    fun provideRemote(): Remote {
        return Remote.create()
    }
}

@Module
class AppModule(context: Context) {
    private val appContext = context

    @Provides
    @Singleton
    fun provideContext(): Context {
        return appContext
    }
}