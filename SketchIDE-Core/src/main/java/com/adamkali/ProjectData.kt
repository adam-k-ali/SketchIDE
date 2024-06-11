package com.adamkali

import java.nio.file.Path
import kotlin.io.path.forEachDirectoryEntry
import kotlin.io.path.isDirectory

class ProjectData(sourceDir : String) {
    // List of source directories
    val sourceDirectories = mutableListOf<String>()
    val sourcePackages = mutableListOf<ProjectPackage>()
//    // Source file types
//    val fileTypes = listOf("kt", "java", "xml", "json", "gradle", "properties")

    // constructor
    init {
        println("ProjectData created")
        // Add some source files
        sourceDirectories.add(sourceDir)
    }

    private fun printPackages(packages: List<ProjectPackage>, indent: Int = 0) {
        for (pkg in packages) {
            println("${" ".repeat(indent)}${pkg.packageName}")
            printPackages(pkg.subPackages, indent + 2)
            for (sourceFile in pkg.sourceFiles) {
                println("${" ".repeat(indent + 2)}$sourceFile")
            }
        }
    }

//    /**
//     * Create the package tree
//     */
//    private fun createPackages() {
//        // Clear the source packages
//        sourcePackages.clear()
//        // Walk through the source files
//        for (sourceFile in sourceFiles) {
//            val sourceFileParts = sourceFile.split("/")
//            val sourceFileName = sourceFileParts.last()
//            var currentPackage = sourcePackages
//            for (i in 0 until sourceFileParts.size - 1) {
//                val packageName = sourceFileParts[i]
//                val packageIndex = currentPackage.indexOfFirst { it.packageName == packageName }
//                if (packageIndex == -1) {
//                    val newPackage = ProjectPackage(packageName)
//                    newPackage.sourceFiles.add(sourceFileName)
//                    currentPackage.add(newPackage)
//                    currentPackage = newPackage.subPackages
//                } else {
//                    currentPackage[packageIndex].sourceFiles.add(sourceFileName)
//                    currentPackage = currentPackage[packageIndex].subPackages
//                }
//            }
//        }
//
//        printPackages(sourcePackages)
//    }

    private fun createPackage(rootPath : String = "testProject/src", rootName: String = "testProject/src"): ProjectPackage {
        var rootPackage = ProjectPackage(rootName)
        // Find all directories in "src"
        Path.of(rootPath).forEachDirectoryEntry { dir ->
            val fileName = dir.fileName.toString()
            if (dir.isDirectory()) {
                println("Found package $fileName. Path: $dir")
                rootPackage.subPackages.add(createPackage(dir.toString(), fileName))
            } else {
                println("Found file $fileName. Path: $dir")
                rootPackage.sourceFiles.add(fileName)
            }
        }

        return rootPackage
    }

    fun refresh() {
        println("Refreshing project data")
        for (sourceDirectory in sourceDirectories) {
            sourcePackages.add(createPackage(sourceDirectory, sourceDirectory))
        }
        printPackages(sourcePackages)

    }
}