package com.example.atomicswap.feature.settings.donate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.ui.HeaderStick
import com.example.atomicswap.core.common.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateView(onBack: () -> Unit) {
    Column {
        TopAppBar(
            windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
                Text(
                    text = stringResource(R.string.terms_title),
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )


    }
}

@Preview
@Composable
fun TermsView_Preview() {
    AppTheme {
        DonateView(
            onBack = {}
        )
    }
}