package com.adamkali.browser

import com.adamkali.ProjectPackage

object FolderTreeAdapter {
    fun convertToFolderNode(rootPackage: ProjectPackage): FolderNode {
        val root = FolderNode(rootPackage.packageName)
        for (subPackage in rootPackage.subPackages) {
            root.subFolders.add(convertToFolderNode(subPackage))
        }
        root.files = rootPackage.sourceFiles
        return root
    }
}