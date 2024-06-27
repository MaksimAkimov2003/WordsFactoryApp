package com.akimov.wordsfactory.feature.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.akimov.domain.dictionary.useCase.GetStudiedWordsFlowUseCase
import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountFlowUseCase
import org.koin.java.KoinJavaComponent.inject

class AppWidget : GlanceAppWidget() {
    private val getWordsInDictionaryCountFlowUseCase by inject<GetWordsInDictionaryCountFlowUseCase>(
        GetWordsInDictionaryCountFlowUseCase::class.java
    )

    private val getStudiedWordsFlowUseCase by inject<GetStudiedWordsFlowUseCase>(
        GetStudiedWordsFlowUseCase::class.java
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val allWordsCount by getWordsInDictionaryCountFlowUseCase().collectAsState(initial = 0)
//            val studiedWordsCount by getStudiedWordsFlowUseCase().collectAsState(initial = 0)

            androidx.glance.layout.Column(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = Color.White,
                    )
            ) {
                androidx.glance.text.Text(
                    text = "WordsFactory $allWordsCount",
                    modifier = GlanceModifier.padding(bottom = 16.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = ColorProvider(Color.Black)
                    )
                )

                androidx.glance.layout.Row {
                    androidx.glance.text.Text(text = "My Dictionary")
//                    Spacer(modifier = GlanceModifier.weight(1f))
                    androidx.glance.text.Text(text = "$0 words")
                }

                androidx.glance.layout.Spacer(modifier = GlanceModifier.height(16.dp))

                androidx.glance.layout.Row {
                    androidx.glance.text.Text(text = "Remembered Words")
//                    Spacer(modifier = GlanceModifier.weight(1f))
                    androidx.glance.text.Text(text = "$0 words")
                }
            }
        }
    }
}
