package ru.vainahtelecom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Objects;

public class AppConfig {

    private static AppConfig instance = null;

    private static final String configPath = "WEB-INF/Config.yaml";

    public synchronized static AppConfig getGlobalConfig() {
        return instance;
    }


    public synchronized static void init(ServletContext context) {
        if (Objects.isNull(instance)) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                instance = mapper.readValue(new File(context.getRealPath(configPath)), AppConfig.class);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private SnmpParam snmp = null;

    public SnmpParam getSnmp() {
        return snmp;
    }

    // Nested class
    public static class SnmpParam {
        private String community = null;

        public String getCommunity() {
            return community;
        }
    }
}
