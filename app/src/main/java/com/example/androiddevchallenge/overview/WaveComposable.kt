/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.overview

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import kotlin.math.abs

/**
 * Design is inspired with https://dribbble.com/shots/5967252-Tab-Bar-Animation
 * TODO: Do more generic and customizable
 */
private const val UNSELECTED_ALPHA = 0.5f

private val ICONS = arrayOf(
    R.drawable.ic_tab_dogs,
    R.drawable.ic_tab_cats,
)

@Composable
fun WaveComposable(
    itemsCount: Int,
    current: State<Int>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val waveWidth = with(LocalDensity.current) { 112.dp.toPx() }
    val fabSize = with(LocalDensity.current) { 56.dp.toPx() }
    val fabGap = with(LocalDensity.current) { 8.dp.toPx() }
    val fabIconSize = with(LocalDensity.current) { 24.dp.toPx() }
    val fabIconHalfSize = fabIconSize * 0.5f

    val fabsHProgress = remember(itemsCount) { Array(itemsCount) { 0f } }
    val fabsVProgress = remember(itemsCount) { Array(itemsCount) { 0f } }
    val width = remember(itemsCount) { mutableStateOf(0) }
    val height = remember(itemsCount) { mutableStateOf(0) }

    val duration = 300

    val spec = tween<Float>(
        duration,
        easing = {
            when {
                it < 0.25f -> {
                    0f
                }
                it > 0.75f -> {
                    1f
                }
                else -> {
                    (it - 0.25f) * 2f
                }
            }
        }
    )

    val spec2 = tween<Float>(
        duration,
        easing = {
            if (it < 0.25f) {
                it * 4f
            } else {
                1f
            }
        }
    )

    val spec3 = tween<Float>(
        duration,
        easing = {
            if (it < 0.75f) {
                0f
            } else {
                (it - 0.75f) * 4f
            }
        }
    )

    val section = 1f / itemsCount
    val halfSection = section * 0.5f
    val target = halfSection + section * current.value
    val waveHProgress = animateFloatAsState(target, spec)
    val waveVProgress = animateFloatAsState(
        if (waveHProgress.value == 0.25f || waveHProgress.value == 0.75f) {
            1f
        } else {
            0f
        }
    )

    repeat(itemsCount) {
        fabsHProgress[it] = halfSection + section * it
        fabsVProgress[it] = animateFloatAsState(
            if (it == current.value) 1f else 0f,
            if (it == current.value) spec3 else spec2
        ).value
    }

    Box {
        Surface(
            shape = createWaveShape(
                waveWidth,
                waveHProgress.value,
                waveVProgress.value,
                fabSize,
                fabGap,
                fabsHProgress,
                fabsVProgress
            ),
            elevation = 8.dp,
            color = MaterialTheme.colors.primary,
            modifier = modifier
                .fillMaxWidth()
                .height(112.dp)
                .onSizeChanged {
                    width.value = it.width
                    height.value = it.height
                }
                .clickable {
                    onClick.invoke(current.value + 1)
                }
                .pointerInteropFilter {
                    if (it.action == MotionEvent.ACTION_UP) {
                        val sectionWidth = width.value / itemsCount
                        val sectionIndex = (it.x / sectionWidth).toInt()
                        onClick.invoke(sectionIndex)
                    }
                    true
                }
        ) {
            Box {
                repeat(itemsCount) {
                    Icon(
                        painter = painterResource(ICONS[it]),
                        contentDescription = stringResource(id = R.string.a11y_tab_icon),
                        tint = Color.White,
                        modifier = Modifier.graphicsLayer(
                            alpha = UNSELECTED_ALPHA + (1 - UNSELECTED_ALPHA) * fabsVProgress[it],
                            translationX = width.value * fabsHProgress[it] - fabIconHalfSize,
                            translationY = height.value * 0.5f + fabIconHalfSize - fabIconSize * fabsVProgress[it]
                        )
                    )
                }
            }
        }
    }
}

fun createWaveShape(
    waveWidth: Float,
    waveHProgress: Float,
    waveVProgress: Float,
    fabSize: Float,
    fabGap: Float,
    fabsHProgress: Array<Float>,
    fabsVProgress: Array<Float>
) = object : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val halfHeight = size.height * 0.5f
        val halfFabSize = fabSize * 0.5f

        val wavePath = Path().apply {
            val halfWaveWidth = waveWidth * 0.5f
            val waveX = size.width * waveHProgress
            val waveYTop = halfHeight - halfFabSize * 0.65f
            val waveYBottom = halfHeight + halfFabSize + fabGap
            val waveY = waveYTop + (waveYBottom - waveYTop) * waveVProgress
            val waveXDelta = abs(waveY - halfHeight)

            moveTo(0f, halfHeight)
            lineTo(waveX - halfWaveWidth, halfHeight)
            cubicTo(
                waveX - halfFabSize, halfHeight,
                waveX - waveXDelta, waveY,
                waveX, waveY
            )
            cubicTo(
                waveX + waveXDelta, waveY,
                waveX + halfFabSize, halfHeight,
                waveX + halfWaveWidth, halfHeight
            )
            lineTo(size.width, halfHeight)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)

            close()
        }
        val fabsPath = Path().apply {
            fabsHProgress.forEachIndexed { index, hProgress ->
                val vProgress = fabsVProgress[index]
                val fabX = size.width * hProgress
                val fabY = halfHeight + halfFabSize - halfFabSize * vProgress
                addOval(Rect(Offset(fabX, fabY), halfFabSize))
            }
            close()
        }
        return Outline.Generic(Path.combine(PathOperation.union, wavePath, fabsPath))
    }
}
