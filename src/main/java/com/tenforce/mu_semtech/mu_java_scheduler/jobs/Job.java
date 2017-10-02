package com.tenforce.mu_semtech.mu_java_scheduler.jobs;


import com.tenforce.mu_semtech.db_support.DbSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Job implements Runnable {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(Job.class);
  private static String localBaseUrl= new DbSupport().getCurrentEnvironmentProperty("local.server.url");



//    "*/5 * * * * ?" // Every 30 seconds FOR TESTING
//    "1 0 0 * * ?" // Daily at one past midnight
    private String cron = "1 0 0 * * ?";

    private String type, url, data = "{}";

    public Job(){}

    public Job(String cron, String type, String url, String data) {
        this.cron = cron;
        this.type = type;
        this.url = url;
        this.data = data;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public void run() {
        final String charset = "UTF-8";
        String baseUrl = (StringUtils.isBlank(localBaseUrl) ? System.getenv("BASE_URL") : localBaseUrl);
        String actualUrl = (url.startsWith("/") ? baseUrl : "") + url;
        HttpURLConnection connection =null;
        try {
            connection = (HttpURLConnection) new URL(actualUrl).openConnection();
            switch (type.toLowerCase().trim()){
                case "get":
                    connection.setRequestProperty("Accept-Charset", charset);
                    break;
                case "delete":
                    connection.setRequestMethod("DELETE");
                    connection.setRequestProperty("Accept-Charset", charset);
                    break;
                case "post":
                    connection.setDoOutput(true); // Triggers POST
                    connection.setRequestProperty("Accept-Charset", charset);
                    connection.setRequestProperty("Content-Type", "application/json");
                    try (OutputStream output = connection.getOutputStream()) {
                        output.write(data.getBytes(charset));
                    }
                    break;
                default:
                    log.warn("Mu-scheduler does not support " + type + " of job: " + this.toString());
                    return;
            }

            if (connection.getResponseCode() < 400) {
                log.info("Job succeeded (" + connection.getResponseCode() + "): " + this.toString());
            }

        }
        catch (Exception e){
            log.warn("Job failed: " + this.toString() + " with error message: " + e.getMessage());
        }
        finally {
          if(connection!= null){
            connection.disconnect();
          }
        }



    }

    @Override
    public String toString() {
        return "Job{" +
                "cron='" + cron + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
