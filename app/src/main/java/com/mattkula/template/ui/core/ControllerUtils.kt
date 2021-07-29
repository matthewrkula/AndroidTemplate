package com.mattkula.template.ui.core

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

/**
 * Duplicate of viewModel() but renamed for our [Controller] class.
 */
@Composable
inline fun <reified VM : ViewModel> controller(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    factory: ViewModelProvider.Factory? = null
): VM = androidx.lifecycle.viewmodel.compose.viewModel(VM::class.java, viewModelStoreOwner, key, factory)
