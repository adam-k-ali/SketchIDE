package com.adamkali.browser

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class FolderNode(var name: String,
                 var expanded: MutableState<Boolean> = mutableStateOf(false),
                 var subFolders : MutableList<FolderNode> = mutableListOf(),
                 var files : MutableList<String> = mutableListOf()) {
    fun toggle() {
        expanded.value = !expanded.value
    }
}