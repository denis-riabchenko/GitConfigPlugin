package com.epam.gradle

import com.epam.gradle.git.GitConfigHelper
import org.apache.commons.configuration.ConfigurationException
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class GitConfigMergeTask extends DefaultTask {
    String sourceFileName;
    String originalFileName;
    String destinationFileName;

    @TaskAction
    def action() {
        if (originalFileName == null) {
            originalFileName = destinationFileName
        }
        Map<String, Map<String, String>> config = null;
        try {
            config = GitConfigHelper.readConfig(originalFileName, null);
        } catch (ConfigurationException ex) {
            // Do nothing (original file can be absent)
        }
        config = GitConfigHelper.readConfig(sourceFileName, config);
        save(config);
    }

    def save(Map<String, Map<String, String>> config) {
        GitConfigHelper.writeConfig(destinationFileName, config);
    }
}
