package com.example.composeissuemasliaev.container

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlin.math.abs

@Composable
fun Card(
    state: State,
    rotationAngle: Float,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onFieldClick: () -> Unit,
    frozenAnimationFinished: () -> Unit,
) {
    val borderBrush = remember {
        Brush.linearGradient(
            colorStops =
            listOf(
                0f to Color(0xFFFFFFFF),
                0.3f to Color(0xFFCBCBDA),
                0.6f to Color(0xFFFFFFFF),
                1f to Color(0xFFCBCBDA),
            ).toTypedArray(),
            start = Offset(Float.POSITIVE_INFINITY, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY),
        )
    }

    val dencity = LocalDensity.current.density
    val sideModifier =
        modifier
            .widthIn(min = 240.dp)
            .aspectRatio(1.5819f)
            .graphicsLayer(rotationY = rotationAngle, cameraDistance = 8 * dencity)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = !state.flagTwo,
                onClick = {},
            )

    Box {
        val needRenderBackSide = remember(abs(rotationAngle % 360f)) {
            abs(rotationAngle % 360f) in 90f..270f
        }
        BackSide(
            state = state.backState,
            modifier = sideModifier
                .graphicsLayer {
                    alpha = if (needRenderBackSide) 1f else 0f
                }
                .shadow(
                    elevation = 34.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = Color(0xFF202F4E),
                    spotColor = Color(0x66202F4E),
                )
                .border(width = 0.5.dp, brush = borderBrush, shape = RoundedCornerShape(20.dp))
                .graphicsLayer(rotationY = 180f)
                .zIndex(if (needRenderBackSide) 1f else 0f),
            onFieldClick = onFieldClick,
            isFrozen = state.flagOne,
            frozenAnimationFinished = frozenAnimationFinished,
        )
        FrontSide(
            modifier = sideModifier
                .graphicsLayer {
                    alpha = if (needRenderBackSide) 0f else 1f
                }
                .shadow(
                    elevation = 34.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = Color(0xFF202F4E),
                    spotColor = Color(0x66202F4E),
                )
                .border(width = 0.5.dp, brush = borderBrush, shape = RoundedCornerShape(20.dp))
                .zIndex(if (needRenderBackSide) 0f else 1f),
            state = state.frontState,
            isFrozen = state.flagOne,
            frozenAnimationFinished = frozenAnimationFinished,
        )
    }
}

@Composable
private fun FrontSide(
    state: State.FrontState,
    modifier: Modifier = Modifier,
    isFrozen: Boolean,
    frozenAnimationFinished: () -> Unit,
) {
    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(20.dp)),
    ) {
        BottomStartContainer(
            modifier =
            Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 20.dp)
                .zIndex(1f),
            lastDigits = state.someString,
            type = state.type,
        )
    }
}

@Composable
private fun BottomStartContainer(
    lastDigits: String,
    type: State.Type,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Type(
            state = type,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = lastDigits,
            style = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            ),
            color = Color(0xFF121416),
        )
    }
}

@Composable
private fun Type(
    state: State.Type,
    modifier: Modifier = Modifier,
) {
    val contentColor = Color(0xFF9E9BA6)
    Row(modifier = modifier) {
        if (state.iconResId != null) {
            Icon(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = painterResource(id = state.iconResId),
                contentDescription = null,
                tint = contentColor,
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = state.valueResId,
            style = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 18.sp,
            ),
            color = contentColor,
        )
    }
}

@Composable
private fun BackSide(
    state: State.BackState?,
    modifier: Modifier = Modifier,
    onFieldClick: () -> Unit,
    isFrozen: Boolean,
    frozenAnimationFinished: () -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            DataField(
                modifier = Modifier.fillMaxWidth(),
                state = state?.fieldOne,
                placeHolder = "Placeholder",
                onClick = {},
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                DataField(
                    modifier = Modifier.weight(1f),
                    state = state?.fieldThree,
                    placeHolder = "Placeholder",
                    onClick = {},
                )

                DataField(
                    modifier = Modifier.weight(1f),
                    state = state?.fieldTwo,
                    placeHolder = "Placeholder",
                    onClick = {},
                )
            }
        }
    }
}
