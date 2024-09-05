package org.mutu.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mutu.example.dto.TaskDefinition;

public class CronJob implements Runnable {

	private TaskDefinition taskDefinition;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void run() {
        System.out.println(format.format(new Date()) + " : " + taskDefinition.getApiUrl() + " - " + taskDefinition.getParam());
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
