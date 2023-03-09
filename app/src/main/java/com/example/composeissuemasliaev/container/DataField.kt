package com.example.composeissuemasliaev.container

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun DataField(
    modifier: Modifier = Modifier,
    state: State.Field?,
    placeHolder: String,
    onClick: (State.Field) -> Unit,
) {
    val borderGradient =
        remember {
            Brush.linearGradient(
                colorStops = listOf(
                    0.0f to Color(0x0D000000),
                    0.5f to Color(0x00000000),
                    1.0f to Color(0x0D000000),
                ).toTypedArray(),
                start = Offset(Float.POSITIVE_INFINITY, 0f),
                end = Offset(0f, Float.POSITIVE_INFINITY),
            )
        }
    val stateIsNotNull by remember(state) {
        derivedStateOf { state != null }
    }
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x0A002570))
            .border(
                width = 1.dp,
                brush = borderGradient,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(
                onClick = onClick@{
                    state ?: return@onClick
                    onClick(state)
                },
                enabled = stateIsNotNull,
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val contentModifier = Modifier.weight(1f)
        if (stateIsNotNull) {
            CardData(modifier = contentModifier, state = requireNotNull(state))
        } else {
            Placeholder(modifier = contentModifier, state = placeHolder)
        }
    }
}

@Composable
private fun Placeholder(
    modifier: Modifier = Modifier,
    state: String,
) {
    Text(
        modifier = modifier,
        text = state,
        color = Color(0xFF9E9BA6),
        style = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 18.sp,
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun CardData(
    modifier: Modifier = Modifier,
    state: State.Field,
) {
    Text(
        modifier = modifier,
        text = state.value,
        color = Color(0xFF1D2026),
        style = TextStyle(
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 18.sp,
        ),
        maxLines = 1,
    )
}
