package com.tenforce.mu_semtech.mu_java_scheduler.jobs;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class JobService
{
    private ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    private Jobs jobs;

    public Jobs getJobs() {
        return jobs;
    }

    @PostConstruct
    public String startup(){
        try {
            jobs = JobReader.read();
            scheduleJobs();
            return jobs.toString();
        } catch (IOException e) {
            Logger.getAnonymousLogger().warning("Can't read job file: " + e);
            return e.toString();
        }
    }

    public void scheduleJobs(){
        taskScheduler.initialize();

        List<Job> jobList = jobs.getJobs();
        Logger.getAnonymousLogger().info("Scheduling " + jobList.size() + " jobs...");

        for (Job j: jobList) {
            taskScheduler.schedule(j, new CronTrigger(j.getCron()));
        }
    }


    public void runJobs(){
        List<Job> jobList = jobs.getJobs();
        Logger.getAnonymousLogger().info("Running " + jobList.size() + " scheduled jobs...");

        for (Job j: jobList) {
            j.run();
        }
    }

    public void stop() {
        taskScheduler.shutdown();
        taskScheduler.initialize();
        Logger.getAnonymousLogger().info("Jobs stopped");
    }
}
