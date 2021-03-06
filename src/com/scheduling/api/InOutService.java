package com.scheduling.api;

import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

public interface InOutService {

    void getProjectNameInput(Project project);

    int getNoOfProjectPlansInput();

    void getProjectPlanNameInput(ProjectPlan projectPlan, int index);

    void getProjectPlanStartDateInput(ProjectPlan projectPlan);

    int getNoOfTasksInput();

    void getTaskNameInput(ProjectPlan projectPlan, Task task, int index);

    int getNoOfDaysToCompleteTask(Task task);

    void getTaskDependenciesInput(ProjectPlan projectPlan, Task task);

    void outputProjectPlanSchedule(ProjectPlan projectPlan);

    void outputOverallProjectSchedule(Project project);

    void outputCreateProjectPlanNotice();

    void outputCreateTaskNotice(ProjectPlan projectPlan);

    void outputEndTaskCreationNotice(ProjectPlan projectPlan);
}
