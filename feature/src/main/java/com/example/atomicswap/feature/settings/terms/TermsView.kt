package com.example.atomicswap.feature.settings.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.component.HeaderStick
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsView(onBack: () -> Unit) {
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

        val content = remember {
            listOf(
                R.string.terms_section_1_title to R.string.terms_section_1_content,
                R.string.terms_section_2_title to R.string.terms_section_2_content,
                R.string.terms_section_3_title to R.string.terms_section_3_content,
                R.string.terms_section_4_title to R.string.terms_section_4_content,
                R.string.terms_section_5_title to R.string.terms_section_5_content,
                R.string.terms_section_6_title to R.string.terms_section_6_content,
                R.string.terms_section_7_title to R.string.terms_section_7_content,
                R.string.terms_section_8_title to R.string.terms_section_8_content,
                R.string.terms_section_9_title to R.string.terms_section_9_content,
            )
        }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = stringResource(
                        R.string.terms_last_updated,
                        stringResource(R.string.terms_default_date)
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            content.forEach { section ->
                stickyHeader {
                    HeaderStick(stringResource(section.first))
                }

                item {
                    Text(
                        text = stringResource(section.second),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.terms_footer),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

        }
    }
}

@Preview
@Composable
fun TermsView_Preview() {
    AppTheme {
        TermsView(
            onBack = {}
        )
    }
}