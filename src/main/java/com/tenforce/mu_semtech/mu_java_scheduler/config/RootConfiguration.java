package com.tenforce.mu_semtech.mu_java_scheduler.config;

import com.tenforce.mu_semtech.mu_java_scheduler.jobs.JobService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackageClasses = {JobService.class})
@EnableAsync
@EnableScheduling
public class RootConfiguration {


}
