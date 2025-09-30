package com.exxlexxlee.atomicswap.feature.settings.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.HeaderStick
import com.exxlexxlee.atomicswap.core.common.ui.TopAppBar
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.settings.terms.models.Event
import com.exxlexxlee.atomicswap.feature.settings.terms.models.ViewState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    Column {
        TopAppBar(stringResource(R.string.terms_title)) {
            eventHandler.invoke(Event.PopBackStack)
        }

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
                )
            }

            item {
                Button(
                    onClick = { eventHandler(Event.ReadTerms) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    enabled = !viewState.isTermsOfUseRead
                ) {
                    Text(
                        text = stringResource(R.string.terms_accept_button).uppercase(),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun TermsView_Preview() {
    AppTheme {
        TermsView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}