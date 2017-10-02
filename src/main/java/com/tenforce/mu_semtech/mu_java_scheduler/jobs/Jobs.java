package com.tenforce.mu_semtech.mu_java_scheduler.jobs;

import java.util.ArrayList;


public class Jobs {

    private ArrayList<Job> jobs;

    public Jobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public Jobs(){}

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }



    @Override
    public String toString() {
        String result = "jobs=\n";
        for (Job job : jobs) {
            result += job.toString() + "\n";
        }
        return result;
    }
}
