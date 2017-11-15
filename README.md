Java-Scheduler
===

A microservice that schedules calls to the other microservices.

Configurable job list
---

The job list is a JSON file. GET, POST and DELETE are supported types of jobs.

For POST request, the data body can be specified.

Each job can have an individual scheduling using a cron-like syntax. See below.

### Example:

```
{"jobs":[
  {"type":"get","url":"/indexer/index/update/conceptScheme?conceptScheme=6b73f82c-2543-4a72-a86d-e988869df5ca"},
  {"type":"delete","url":"/cleanup/clean?delete=validations,creation"},
  {"type":"post","url":"/validation/run", "data":"{}", "cron":"* */15 * * * ?" }
]}
```

HTTP endpoint
---
`/` Reload the config file. Lists the loaded jobs or any problems, or any errors that came from reading the file.

`/runNow` Instantly run the jobs that are currently loaded. Lists the loaded jobs.

`/stop` Stop all scheduled jobs.

Individual scheduling
---

By default each job runs at midnight. A job can be given its own scheduling using a cron-like syntax, as used by [Java Spring](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html#scheduling-trigger-implementations).

Main differences with standard cron are:

 * A field for seconds at the beginning.
 * Slash to specify step values. E.g `*/5` in the minutes field indicates every 5 minutes.
 * A field for days of the week at the end. Use `?` for every day.

Building
--------

```
docker build -t java-scheduler .
```

Running
-------

```
docker run -it --rm --name scheduler \
    -v "$PWD"/example:/config \
    -e BASE_URL="http://application" \
    java-scheduler
```

Suggested improvements
---
 * Add sparql jobs. This will allow for nicer cleanup when the diff-service is built.
