package com.exxlexxlee.atomicswap.core.common.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.R
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedFAB(show: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = show,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 500)
        ) + fadeIn(animationSpec = tween(500)),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(500)
        ) + fadeOut(animationSpec = tween(500))
    ) {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = Color.Transparent,
            modifier = Modifier
                .size(56.dp)
                .shadow(8.dp, CircleShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4CAF50),
                            MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    ),
                    shape = CircleShape
                )
        ) {
            Icon(
                painterResource(R.drawable.outline_note_stack_add_24),
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview
@Composable
fun AnimatedFAB_PreView() {
    AppTheme {
        AnimatedFAB(true) { }
    }
}