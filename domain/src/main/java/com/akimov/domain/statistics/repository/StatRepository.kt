package com.akimov.domain.statistics.repository

interface StatRepository {
    /**
     * @return Unix-time
     */
    fun getLastTrainingDate(): Long

    /**
     * @param date Unix-time
     */
    fun setLastTrainingDate(date: Long)
}