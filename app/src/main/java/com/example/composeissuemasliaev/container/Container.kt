package com.example.composeissuemasliaev.container

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun Container(
    cardState: State,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            state = cardState,
            rotationAngle = 180f,
            onFieldClick = {},
            frozenAnimationFinished = {},
        )
        Spacer(modifier = Modifier.height(12.dp))
        ExplanatoryTextContainer(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .wrapContentSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ExplanatoryTextContainer(modifier: Modifier) {
    val brush = getAnimatedBrush()
    Row(
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(brush = brush, blendMode = BlendMode.SrcIn)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "String 1",
            style = TextStyle(
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            ),
            color = Color.Unspecified,
        )
    }
}

@Composable
private fun getAnimatedBrush(): Brush {
    val density = LocalDensity.current
    val endValue = with(density) { 280.dp.toPx() }
    val infiniteTransition = rememberInfiniteTransition()
    val transition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = endValue,
        animationSpec = infiniteRepeatable(tween(1000)),
    )
    val halfEnd = endValue / 2
    return Brush.linearGradient(
        colorStops = arrayOf(
            0.82f to Color(0x73E5EEFF),
            1f to Color(0x73E5EEFF).copy(alpha = 0.01f),
        ),
        start = Offset(transition, transition),
        end = Offset(transition + halfEnd, transition + halfEnd),
        tileMode = TileMode.Mirror,
    )
}
