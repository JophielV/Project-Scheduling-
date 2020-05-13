package com.scheduling.api;

import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

public interface AppService {

    void getProjectNameInput(Project project);

    int getNoOfProjectPlansInput();

    void getProjectPlanNameInput(ProjectPlan projectPlan, int index);

    int getNoOfTasksInput();

    void getTaskNameInput(Task task, int index);

    int getNoOfDaysToCompleteTask(Task task);

    void getTaskDependenciesInput(ProjectPlan projectPlan, Task task);

    void outputFinalSchedule(Project project);

    void outputCreateProjectPlanNotice();

    void outputCreateTaskNotice(ProjectPlan projectPlan);

    void outputEndTaskCreationNotice(ProjectPlan projectPlan);
}
