package com.adamkali.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.adamkali.ProjectManager

@Composable
fun folderButton(folder : FolderNode, level : Int) {
    Row(modifier = Modifier.padding(start = 40.dp * level)) {
        TextButton(
            modifier = Modifier.size(20.dp, 20.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = { folder.toggle() }) {
            if (folder.expanded.value) {
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = null)
            } else {
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = null, modifier = Modifier.rotate(270f))
            }
        }
        Text(text = folder.name)
    }
}

@Composable
fun fileButton(file : String, level: Int) {
    Row (modifier = Modifier.padding(start = 40.dp * level)){
        TextButton(
            contentPadding = PaddingValues(0.dp),
            onClick = { }) {
            Text(text = file)
        }
    }
}

@Composable
fun projectFileTree(pkg : FolderNode, level : Int = 0) {
    Column {
        folderButton(pkg, level)
        if (pkg.expanded.value) {
            for (subPackage in pkg.subFolders) {
                projectFileTree(subPackage, level + 1)
            }
            for (sourceFile in pkg.files) {
                fileButton(sourceFile, level + 1)
            }
        }
    }
}

@Composable
fun projectBrowser() {
    val rootFolders : MutableList<FolderNode> = mutableListOf()
    ProjectManager.projectData.sourcePackages.forEach {
        rootFolders.add(FolderTreeAdapter.convertToFolderNode(it))
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text("Project Browser")
            rootFolders.forEach {
                projectFileTree(it)
            }
        }
    }
}