package com.epam.gradle

import com.epam.gradle.git.GitConfigHelper

class GitConfigPrintTask extends GitConfigMergeTask {
    @Override
    def save(Map<String, Map<String, String>> config) {
        GitConfigHelper.writeConfig(config,
                {String section -> println String.format("[%s]", section)},
                {String key, String value -> println String.format("\t%s = %s", key, value)});
    }
}
