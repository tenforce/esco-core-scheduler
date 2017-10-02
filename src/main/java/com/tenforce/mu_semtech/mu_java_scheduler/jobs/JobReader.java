package com.tenforce.mu_semtech.mu_java_scheduler.jobs;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JobReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Jobs read() throws IOException{
        String config_dir = System.getProperty("CONFIG_DIR_SCHEDULER");
        File file = new File((StringUtils.isBlank(config_dir) ? "/config" :  config_dir ) + "/jobs.conf");
        Jobs jobs = mapper.readValue(file, Jobs.class);
        return jobs;
    }

}
