package com.akimov.data.dictionary.api

import com.akimov.data.dictionary.model.WordModel
import retrofit2.http.GET
import retrofit2.http.Path

interface WordsService {
    @GET("entries/en/{query}")
    suspend fun searchWord(
        @Path("query")
        query: String
    ): List<WordModel>
}