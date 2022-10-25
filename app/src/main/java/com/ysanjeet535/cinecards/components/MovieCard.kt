package com.ysanjeet535.cinecards.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

sealed class CardFace {
    object Front : CardFace() {
        override val next: CardFace
            get() = Back
        override val angle: Float = 0f
    }

    object Back : CardFace() {
        override val next: CardFace
            get() = Front
        override val angle: Float = 180f
    }

    abstract val angle: Float
    abstract val next: CardFace
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {

    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing,
        )
    )

    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .size(120.dp)
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            if (rotation.value <= 90f) {
                front()
            } else {
                Box(
                    Modifier
                        .wrapContentSize()
                        .graphicsLayer {
                            rotationY = 180f
                        },
                ) {
                    back()
                }
            }
        }
    }
}