package com.akimov.wordsfactory.common.extensions

import androidx.compose.ui.Modifier

/**
 * Если нужно проверить условие и в зависимости от проверки использовать разный modifer.
 */
inline fun Modifier.checkCondition(
    condition: () -> Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier {
    return if (condition()) {
        then(ifTrue(Modifier))
    } else {
        then(ifFalse(Modifier))
    }
}