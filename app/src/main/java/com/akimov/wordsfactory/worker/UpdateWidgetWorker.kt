package com.akimov.wordsfactory.worker

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.akimov.wordsfactory.feature.widget.AppWidget

class UpdateWidgetWorker(
    private val context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(
    context,
    parameters
) {
    override suspend fun doWork(): Result {
        Log.i("UpdateWidgetWorker", "doWork")
        AppWidget().updateAll(context = context)
        return Result.success()
    }
}