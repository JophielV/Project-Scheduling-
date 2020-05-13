package com.scheduling.api;

import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

public interface ProjectPlanService {

    void setProjectPlanEndDate(ProjectPlan projectPlan);

    void addTaskToProjectPlan(ProjectPlan projectPlan, Task task);

}
