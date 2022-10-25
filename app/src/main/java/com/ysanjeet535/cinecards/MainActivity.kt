package com.ysanjeet535.cinecards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ysanjeet535.cinecards.components.CardFace
import com.ysanjeet535.cinecards.components.FlipCard
import com.ysanjeet535.cinecards.ui.theme.CineCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CineCardsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        MovieCardSet()
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieCardSet() {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val transform: Float by animateFloatAsState(if (isExpanded) 60f else 10f)

    var isShowingPreview by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = !isShowingPreview,
        exit = fadeOut(),
        enter = fadeIn()
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Yellow)
                .clickable {
                    isExpanded = !isExpanded
                },
            contentAlignment = Alignment.Center
        ) {
            FlipCard(
                cardFace = CardFace.Front,
                onClick = {
                    isShowingPreview = !isShowingPreview
                },
                front = {
                    SimpleBox(color = Color.Red)
                },
                back = {
                    SimpleBox(color = Color.Blue)
                },
                modifier = Modifier.graphicsLayer {
                    rotationZ = -transform.times(3)
                    translationX = -transform.times(2)
                    translationY = -transform.times(2)
                }
            )
            FlipCard(
                cardFace = CardFace.Front,
                onClick = {
                    isShowingPreview = !isShowingPreview
                },
                front = {
                    SimpleBox(color = Color.Green)
                },
                back = {
                    SimpleBox(color = Color.Blue)
                },
                modifier = Modifier.graphicsLayer {
                    rotationZ = 0f
                    translationX = 0f
                }
            )
            FlipCard(
                cardFace = CardFace.Front,
                onClick = {
                    isShowingPreview = !isShowingPreview
                },
                front = {
                    SimpleBox(color = Color.Gray)
                },
                back = {
                    SimpleBox(color = Color.Blue)
                },
                modifier = Modifier.graphicsLayer {
                    rotationZ = transform.times(3)
                    translationX = transform.times(2)
                    translationY = transform.times(2)
                }
            )
        }
    }

    AnimatedVisibility(
        visible = isShowingPreview,
        exit = fadeOut(),
        enter = fadeIn()
    ) {
        MoviePreview {
            isShowingPreview = !isShowingPreview
        }
    }
}


@Composable
fun MoviePreview(onClick: () -> Unit) {

    var cardFace: CardFace by remember {
        mutableStateOf(CardFace.Front)
    }

    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Yellow)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        FlipCard(
            cardFace = cardFace,
            onClick = {
                cardFace = cardFace.next
            },
            front = {
                SimpleBox(color = Color.Gray)
            },
            back = {
                SimpleBox(color = Color.Blue)
            }
        )

    }
}

@Composable
fun SimpleBox(color: Color) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CineCardsTheme {

    }
}