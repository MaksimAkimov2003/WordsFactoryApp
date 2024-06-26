package com.akimov.wordsfactory.di

import com.akimov.data.words.api.WordsService
import com.akimov.data.words.database.dao.WordsDao
import com.akimov.data.words.mediaPlayer.AppMediaPlayerImpl
import com.akimov.data.words.repository.WordsRepositoryImpl
import com.akimov.domain.dictionary.mediaPlayer.AppMediaPlayer
import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.dictionary.useCase.SaveWordToDictionaryUseCase
import com.akimov.domain.dictionary.useCase.SearchWordUseCase
import com.akimov.domain.dictionary.useCase.StartListenAudioUseCase
import com.akimov.domain.dictionary.useCase.StopListenAudioUseCase
import com.akimov.wordsfactory.RoomDb
import com.akimov.wordsfactory.feature.dictionary.presentation.DictionaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val dictionaryModule = module {
    /** Data */
    single<AppMediaPlayer> {
        AppMediaPlayerImpl(
            mediaPlayer = get()
        )
    }

    single<WordsService> {
        get<Retrofit>().create(WordsService::class.java)
    }

    single<WordsDao> {
        get<RoomDb>().wordsDao()
    }

    factory<WordsRepository> {
        WordsRepositoryImpl(
            api = get(),
            dao = get()
        )
    }

    /** Domain */
    factory {
        StartListenAudioUseCase(
            appMediaPlayer = get()
        )
    }

    factory {
        StopListenAudioUseCase(
            appMediaPlayer = get()
        )
    }

    factory {
        SaveWordToDictionaryUseCase(
            repository = get<WordsRepository>()
        )
    }

    factory {
        SearchWordUseCase(
            repository = get<WordsRepository>()
        )
    }

    /** Presentation */
    viewModel {
        DictionaryViewModel(
            startListenAudioUseCase = get(),
            stopListenAudioUseCase = get(),
            saveWordUseCase = get(),
            searchWordUseCase = get()
        )
    }
}