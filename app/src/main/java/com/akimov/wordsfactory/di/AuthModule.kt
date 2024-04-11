package com.akimov.wordsfactory.di

import android.content.Context
import android.content.SharedPreferences
import com.akimov.data.auth.repository.AuthRepositoryImpl
import com.akimov.domain.auth.repository.AuthRepository
import com.akimov.domain.auth.useCase.CheckUserAuthUseCase
import com.akimov.domain.auth.useCase.LoginUserUseCase
import com.akimov.domain.auth.useCase.RegisterUserUseCase
import com.akimov.wordsfactory.screens.login.LoginViewModel
import com.akimov.wordsfactory.screens.register.RegisterViewModel
import com.akimov.wordsfactory.screens.splash.SplashViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "Auth",
            Context.MODE_PRIVATE
        )
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuth = get(),
            sharedPreferences = get()
        )
    }

    factory {
        RegisterUserUseCase(repository = get())
    }
    factory {
        CheckUserAuthUseCase(repository = get())
    }
    factory {
        LoginUserUseCase(repository = get())
    }

    viewModel {
        RegisterViewModel(registerUserUseCase = get())
    }
    viewModel {
        LoginViewModel(loginUserUseCase = get())
    }
    viewModel {
        SplashViewModel(checkUserAuthUseCase = get())
    }
}