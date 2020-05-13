package com.scheduling.api;

import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

public interface ProjectPlanService {

    void setProjectPlanStartDate(Project project, ProjectPlan projectPlan);

    void computeEndDateOfProjectPlan(ProjectPlan projectPlan);

    void addTaskToProjectPlan(ProjectPlan projectPlan, Task task);

}
