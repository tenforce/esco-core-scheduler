package com.tenforce.mu_semtech.mu_java_scheduler.web;

import com.tenforce.mu_semtech.mu_java_scheduler.jobs.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {

    @Autowired
    private JobService jobService;

    /**
     * Reload the jobs and schedule them.
     * @return a page listing the jobs or any errors that came from reading them.
     */
    @RequestMapping("/")
    public ResponseEntity<String> jobs() {
        jobService.stop();
        String response = jobService.startup();

        response = "<pre>" + response + "</pre>";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Instantly run the jobs that are currently loaded.
     * @return a page listing the jobs.
     */
    @RequestMapping("/runNow")
    public ResponseEntity<String> runNow() {
        jobService.runJobs();
        String response = jobService.getJobs().toString();

        response = "<pre>" + response + "</pre>";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Stop all scheduled jobs.
     */
    @RequestMapping("/stop")
    public ResponseEntity<String> stop() {
        jobService.stop();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
