package com.scheduling;

import com.scheduling.api.AppService;
import com.scheduling.api.ProjectPlanService;
import com.scheduling.api.ProjectService;
import com.scheduling.api.TaskService;
import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;
import com.scheduling.service.AppServiceImpl;
import com.scheduling.service.ProjectPlanServiceImpl;
import com.scheduling.service.ProjectServiceImpl;
import com.scheduling.service.TaskServiceImpl;


public class Main {

    public static void main(String[] args) {
        AppService appService = new AppServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        ProjectPlanService projectPlanService = new ProjectPlanServiceImpl();
        TaskService taskService = new TaskServiceImpl();
        Project project = new Project();

        appService.getProjectNameInput(project);
        int projectPlansNo = appService.getNoOfProjectPlansInput();

        appService.outputCreateProjectPlanNotice();

        for (int i = 0; i < projectPlansNo; i++) {
            ProjectPlan projectPlan = new ProjectPlan();
            appService.getProjectPlanNameInput(projectPlan, i);
            projectPlanService.setProjectPlanStartDate(project, projectPlan);

            int noOfTasks = appService.getNoOfTasksInput();

            appService.outputCreateTaskNotice(projectPlan);

            for (int j = 0; j < noOfTasks; j++) {
                Task task = new Task();
                appService.getTaskNameInput(task, j);
                appService.getNoOfDaysToCompleteTask(task);
                appService.getTaskDependenciesInput(projectPlan, task);
                System.out.println();
                taskService.computeStartAndEndDateOfTask(projectPlan, task);
                projectPlanService.addTaskToProjectPlan(projectPlan, task);
            }
            appService.outputEndTaskCreationNotice(projectPlan);
            projectPlanService.computeEndDateOfProjectPlan(projectPlan);
            projectService.addProjectPlanToProject(project, projectPlan);
        }
        projectService.computeEndDateOfProject(project);

        appService.outputFinalSchedule(project);
    }
}
