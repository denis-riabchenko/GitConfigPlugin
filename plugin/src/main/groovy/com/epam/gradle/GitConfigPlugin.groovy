package com.epam.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GitConfigPlugin implements Plugin<Project> {
    void apply(Project target) {
        target.task('gitConfigPrint', type: GitConfigPrintTask)
        target.task('gitConfigMerge', type: GitConfigMergeTask)
    }
}
