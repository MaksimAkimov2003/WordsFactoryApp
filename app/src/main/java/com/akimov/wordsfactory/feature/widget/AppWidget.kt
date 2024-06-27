package com.akimov.wordsfactory.feature.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import com.akimov.domain.dictionary.useCase.GetStudiedWordsFlowUseCase
import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountFlowUseCase
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.MainActivity
import com.akimov.wordsfactory.common.theme.GlanceH3
import com.akimov.wordsfactory.common.theme.GlanceH5
import com.akimov.wordsfactory.common.theme.GlanceParagraphMedium
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
            val studiedWordsCount by getStudiedWordsFlowUseCase().collectAsState(initial = 0)
            val localContext = LocalContext.current

            androidx.glance.layout.Column(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(
                        imageProvider = ImageProvider(R.drawable.widget_background)
                    )
                    .clickable(
                        actionStartActivity<MainActivity>()
                    )
            ) {
                Box(
                    GlanceModifier
                        .fillMaxWidth()
                        .background(
                            imageProvider = ImageProvider(R.drawable.widget_top_gradient)
                        )
                ) {
                    androidx.glance.text.Text(
                        text = localContext.getString(R.string.app_name),
                        modifier = GlanceModifier.padding(
                            start = 16.dp,
                            top = 5.dp,
                            bottom = 4.dp
                        ),
                        style = GlanceH5
                    )
                }

                androidx.glance.layout.Row(
                    modifier = GlanceModifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = androidx.glance.layout.Alignment.CenterVertically
                ) {
                    androidx.glance.text.Text(
                        text = localContext.getString(R.string.my_dictionary),
                        style = GlanceH3
                    )
                    Box(
                        modifier = GlanceModifier.fillMaxWidth(),
                        contentAlignment = androidx.glance.layout.Alignment.CenterEnd
                    ) {
                        androidx.glance.text.Text(
                            text = buildString {
                                append("$allWordsCount")
                                append(" ")
                                append(localContext.getString(R.string.words))
                            },
                            style = GlanceParagraphMedium
                        )
                    }
                }

                androidx.glance.layout.Row(
                    modifier = GlanceModifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = androidx.glance.layout.Alignment.CenterVertically
                ) {
                    androidx.glance.text.Text(
                        text = localContext.getString(R.string.i_already_remember),
                        style = GlanceH3
                    )
                    Box(
                        modifier = GlanceModifier.fillMaxWidth(),
                        contentAlignment = androidx.glance.layout.Alignment.CenterEnd
                    ) {
                        androidx.glance.text.Text(
                            text = buildString {
                                append("$studiedWordsCount")
                                append(" ")
                                append(localContext.getString(R.string.words))
                            },
                            style = GlanceParagraphMedium
                        )
                    }
                }

                Spacer(modifier = GlanceModifier.height(20.dp))
            }
        }
    }
}
