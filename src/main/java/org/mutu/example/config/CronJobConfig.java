package org.mutu.example.config;

import java.util.List;

import org.mutu.example.dto.TaskDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cronjob")
public class CronJobConfig {

    private List<TaskDefinition> taskDefinitionList;

    public List<TaskDefinition> getTaskDefinitionList() {
        return taskDefinitionList;
    }

    public void setTaskDefinitionList(List<TaskDefinition> taskDefinitionList) {
        this.taskDefinitionList = taskDefinitionList;
    }    
}
