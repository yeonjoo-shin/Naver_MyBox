package com.numble.utils;


import org.apache.commons.configuration2.ex.ConfigurationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

@RequiredArgsConstructor
public class PropertyUtils {

    private static final Configuration configuration;
    private static final String PROPERTIES_MYBOX = "properties/mybox.properties";

    static {
        configuration = PropertyUtils.getConfiguration(PROPERTIES_MYBOX);
    }

    public static String getString(String str) {
        return configuration.getString(str);
    }

    private static Configuration getConfiguration(String path) {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder = (new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class))
                .configure(params.properties().setFileName(path));

        try {
            return builder.getConfiguration();
        }catch (ConfigurationException var7) {
            return null;
        }
    }



}
