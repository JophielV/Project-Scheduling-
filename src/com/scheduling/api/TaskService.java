package com.scheduling.api;

import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getTaskByTaskName(List<Task> tasks, String taskName);

    String getTaskListChoices(ProjectPlan projectPlan);

    void computeStartAndEndDateOfTask(ProjectPlan projectPlan, Task task);
}
