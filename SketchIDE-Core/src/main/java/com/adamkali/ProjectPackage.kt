package com.adamkali

class ProjectPackage(var packageName : String) {
    var sourceFiles = mutableListOf<String>()
    var subPackages = mutableListOf<ProjectPackage>()
}