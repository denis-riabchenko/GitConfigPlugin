package com.epam.gradle.git

import org.apache.commons.collections4.MapUtils
import org.apache.commons.configuration.ConfigurationException
import org.apache.commons.configuration.HierarchicalINIConfiguration
import org.apache.commons.configuration.SubnodeConfiguration
import org.apache.commons.io.IOUtils

class GitConfigHelper {
    def static readConfig(String fileName, Closure readSection, Closure readProperty) {
        HierarchicalINIConfiguration configuration = new HierarchicalINIConfiguration(fileName);

        Set<String> sections = new LinkedHashSet<>();
        // Global section (can be empty)
        sections.add(null);
        sections.addAll(configuration.getSections());
        if (sections != null) {
            for (String sectionName : sections) {
                readSection(sectionName);

                SubnodeConfiguration section = configuration.getSection(sectionName);
                for (String key : section.getKeys()) {
                    readProperty(key, section.getString(key));
                }
            }
        }
    }

    def static readConfig(String fileName, Map<String, Map<String, String>> initialConfig) {
        Map<String, Map<String, String>> config = new LinkedHashMap<>();
        if (initialConfig != null) {
            config.putAll(initialConfig);
        }

        Map<String, String> properties = null;
        readConfig(fileName,
                { String section ->
                    properties = config.get(section);
                    if (properties == null) {
                        //noinspection GrReassignedInClosureLocalVar
                        properties = new LinkedHashMap<>();
                        config.put(section, properties);
                    }
                },
                {String key, String value -> properties.put(key, value)});

        return config;
    }

    def static writeConfig(Map<String, Map<String, String>> config, Closure writeSection, Closure writeProperty) {
        for (String sectionName : config.keySet()) {
            Map<String, String> properties = config.get(sectionName);
            if (sectionName != null || MapUtils.isNotEmpty(properties)) {
                writeSection(sectionName);

                for (Map.Entry<String, String> property : properties.entrySet()) {
                    writeProperty(property.getKey(), property.getValue());
                }
            }
        }
    }

    def static writeConfig(String fileName, Map<String, Map<String, String>> config) {
        HierarchicalINIConfiguration configuration = new HierarchicalINIConfiguration();

        SubnodeConfiguration section = null;
        writeConfig(config,
                {String sectionName -> section = configuration.getSection(sectionName)},
                {String key, String value -> section.addProperty("\t" + key, value)});

        FileWriter writer = null;
        try {
            configuration.save(writer = new FileWriter(fileName, false));
        } catch (IOException ex) {
            throw new ConfigurationException(String.format("Cannot write configuration file %s", fileName), ex);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
