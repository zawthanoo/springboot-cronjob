package org.mutu.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.mutu.example.config.CronJobConfig;
import org.mutu.example.dto.TaskDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleATask(TaskDefinition taskDefinition) {
        CronJob job = new CronJob();
        job.setTaskDefinition(taskDefinition);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(job, new CronTrigger(taskDefinition.getCronExp(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(taskDefinition.getTaskId(), scheduledTask);
    }

    public void removeScheduledTask(String taskId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(taskId);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.remove(taskId);
        }
    }

    @Autowired
    private CronJobConfig cronJobConfig;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("Task List size  " + cronJobConfig.getTaskDefinitionList().size());
        for(TaskDefinition taskDefinition : cronJobConfig.getTaskDefinitionList()) {
            scheduleATask(taskDefinition);
        }
    }
}