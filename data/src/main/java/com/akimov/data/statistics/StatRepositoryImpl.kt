package com.akimov.data.statistics

import android.content.SharedPreferences
import com.akimov.domain.statistics.repository.StatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : StatRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private companion object {
        const val LAST_TRAINING_DATE = "lastTrainingDate"
    }

    override fun getLastTrainingDate(): Long {
        return sharedPreferences.getLong(LAST_TRAINING_DATE, -1)
    }

    override fun setLastTrainingDate(date: Long) {
        coroutineScope.launch {
            sharedPreferences.edit().putLong("lastTrainingDate", date).apply()
        }
    }
}