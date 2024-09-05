package org.mutu.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDefinition {
    private String taskId;
    private String taskName;
    private String cronExp;
	private String apiUrl;
	private String param;
}
