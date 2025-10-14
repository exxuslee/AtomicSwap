package com.exxlexxlee.atomicswap.core.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
    onSearchTextChanged: (String) -> Unit = {},
) {

    var searchMode by remember { mutableStateOf(searchModeInitial) }
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchText by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            Text(
                text = if (searchMode) "" else title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            HsIconButton(onClick = {
                if (searchMode) {
                    searchText = ""
                    onSearchTextChanged.invoke("")
                    searchMode = false
                } else {
                    onClose.invoke()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                    contentDescription = stringResource(R.string.back),
                )
            }
        },
        actions = {
            if (searchMode) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 2.dp)
                        .focusRequester(focusRequester),
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onSearchTextChanged.invoke(it)
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
                    maxLines = 1,
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
                                onSearchTextChanged.invoke("")
                                showClearButton = false
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_arrow_back_ios_new_24),
                                    contentDescription = stringResource(R.string.cancel),
                                )
                            }

                        }
                    },
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }

            if (!searchMode) {
                HsIconButton(
                    onClick = { searchMode = true }

                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_search_24),
                        contentDescription = stringResource(R.string.search),
                    )
                }

                HsIconButton(
                    onClick = { searchMode = true }

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

@Composable
private fun SearchCell(
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit = {},
    placeholder: String = stringResource(R.string.search),
    onSearchClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onSearchClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.outline_search_24),
            contentDescription = "Search",
        )

        HSpacer(16.dp)

        BasicTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchQueryChange.invoke(it)
            },
            modifier = Modifier.weight(1f),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) {
                    Text(
                        text = placeholder,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                innerTextField()
            }
        )
    }
}