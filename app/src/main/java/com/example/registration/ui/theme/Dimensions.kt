package com.example.registration.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SignupDimensions(
    val pageHorizontalPadding16: Dp = 16.dp,
    val pageVerticalPadding08: Dp = 8.dp,
    val itemHorizontalPadding04: Dp = 4.dp,
    val itemHorizontalPadding08: Dp= 8.dp,
    val itemVerticalPadding08: Dp = 8.dp,
    val padding04:Dp=4.dp,
    val padding08:Dp=8.dp,
    val padding16:Dp=8.dp,
    val profileSize: Dp = 100.dp,
    val normalFont16: TextUnit = 16.sp,
    val headingFont20: TextUnit = 20.sp
)

data class Dimensions(
    val signupDimension : SignupDimensions = SignupDimensions()
)

val LocalDimension = compositionLocalOf { Dimensions() }

val MaterialTheme.dimens : Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimension.current


//val MaterialTheme.dimens:ComposeD