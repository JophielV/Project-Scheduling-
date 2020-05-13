package com.scheduling.api;

import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.util.List;

public interface TaskService {

    Task getTaskByTaskName(List<Task> tasks, String taskName);

    void computeStartAndEndDateOfTask(ProjectPlan projectPlan, Task task);
}
