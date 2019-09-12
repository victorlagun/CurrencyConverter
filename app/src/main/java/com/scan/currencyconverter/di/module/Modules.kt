package com.scan.currencyconverter.di.module

import android.content.Context
import androidx.annotation.NonNull
import com.scan.currencyconverter.converter.CurrencyConverterContract
import com.scan.currencyconverter.converter.CurrencyConverterPresenter
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
import com.scan.currencyconverter.util.Formatter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConverterModule {

    @Provides
    fun providePresenter(): CurrencyConverterContract.Presenter {
        return CurrencyConverterPresenter()
    }
}

@Module
class UtilsModule {

    @Provides
    @NonNull
    @Singleton
    fun provideConverter(): Converter {
        return Converter()
    }

    @Provides
    @NonNull
    fun provideFormatter(): Formatter {
        return Formatter()
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