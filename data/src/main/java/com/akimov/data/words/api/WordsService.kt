package com.akimov.data.words.api

import com.akimov.data.words.model.WordModel
import retrofit2.http.GET
import retrofit2.http.Path

interface WordsService {
    @GET("entries/en/{query}")
    suspend fun searchWord(
        @Path("query")
        query: String
    ): List<WordModel>
}