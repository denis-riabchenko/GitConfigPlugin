package com.epam.gradle

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class GitConfigPluginTest {
    @Test
    public void addsTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.epam.gradle.gitConfig'

        assertTrue(project.tasks.gitConfigPrint instanceof GitConfigPrintTask)
        assertTrue(project.tasks.gitConfigMerge instanceof GitConfigMergeTask)
    }
}
