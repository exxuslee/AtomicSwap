package com.exxlexxlee.atomicswap.core.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.R

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun SearchBar(
    title: String,
    searchHintText: String = "",
    searchModeInitial: Boolean = false,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onClose: () -> Unit,
    onChainFilter: () -> Unit,
    onSearchTextChanged: (String) -> Unit = {},
) {
    var searchMode by remember { mutableStateOf(searchModeInitial) }
    var showClearButton by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            if (searchMode) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .focusRequester(focusRequester),
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            onSearchTextChanged(it)
                            showClearButton = it.isNotEmpty()
                        },
                        placeholder = {
                            Text(
                                text = searchHintText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                        }),
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = showClearButton,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                HsIconButton(onClick = {
                                    searchText = ""
                                    onSearchTextChanged("")
                                    showClearButton = false
                                }) {
                                    Icon(
                                        painter = painterResource(R.drawable.outline_cancel_24),
                                        contentDescription = stringResource(R.string.cancel),
                                    )
                                }
                            }
                        }
                    )
                }

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            HsIconButton(onClick = {
                if (searchMode) {
                    searchText = ""
                    onSearchTextChanged("")
                    searchMode = false
                    keyboardController?.hide()
                } else {
                    onClose()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                    contentDescription = stringResource(R.string.back),
                )
            }
        },
        actions = {
            if (!searchMode) {
                HsIconButton(onClick = { searchMode = true }) {
                    Icon(
                        painter = painterResource(R.drawable.outline_search_24),
                        contentDescription = stringResource(R.string.search),
                    )
                }
                HsIconButton(
                    onClick = onChainFilter,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_category_24),
                        contentDescription = stringResource(R.string.blockchain),
                    )
                }
            }
        }
    )
}