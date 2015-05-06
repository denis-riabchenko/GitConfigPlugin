package com.epam.gradle

import com.epam.gradle.GitConfigMergeTask
import com.epam.gradle.GitConfigPrintTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class GitConfigPrintTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()

        def task = project.task('gitConfigPrint', type: GitConfigPrintTask)
        assertTrue(task instanceof GitConfigPrintTask)

        task = project.task('gitConfigMerge', type: GitConfigMergeTask)
        assertTrue(task instanceof GitConfigMergeTask)
    }
}
