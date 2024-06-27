package com.akimov.wordsfactory.di

import com.akimov.wordsfactory.feature.video.presentation.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val videoModule = module {
    viewModelOf(::VideoViewModel)
}