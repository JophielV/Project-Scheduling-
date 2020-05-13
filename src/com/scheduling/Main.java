package com.scheduling;

import com.scheduling.api.InOutService;
import com.scheduling.api.ProjectPlanService;
import com.scheduling.api.ProjectService;
import com.scheduling.api.TaskService;
import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;
import com.scheduling.service.InOutServiceImpl;
import com.scheduling.service.ProjectPlanServiceImpl;
import com.scheduling.service.ProjectServiceImpl;
import com.scheduling.service.TaskServiceImpl;


public class Main {

    public static void main(String[] args) {
        InOutService inOutService = new InOutServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        ProjectPlanService projectPlanService = new ProjectPlanServiceImpl();
        TaskService taskService = new TaskServiceImpl();
        Project project = new Project();

        inOutService.getProjectNameInput(project);
        int projectPlansNo = inOutService.getNoOfProjectPlansInput();
        inOutService.outputCreateProjectPlanNotice();

        for (int i = 0; i < projectPlansNo; i++) {
            ProjectPlan projectPlan = new ProjectPlan();
            inOutService.getProjectPlanNameInput(projectPlan, i);
            projectPlanService.setProjectPlanStartDate(project, projectPlan);

            int noOfTasks = inOutService.getNoOfTasksInput();

            inOutService.outputCreateTaskNotice(projectPlan);

            for (int j = 0; j < noOfTasks; j++) {
                Task task = new Task();
                inOutService.getTaskNameInput(task, j);
                inOutService.getNoOfDaysToCompleteTask(task);
                inOutService.getTaskDependenciesInput(projectPlan, task);
                taskService.computeStartAndEndDateOfTask(projectPlan, task);
                projectPlanService.addTaskToProjectPlan(projectPlan, task);
            }
            inOutService.outputEndTaskCreationNotice(projectPlan);
            projectPlanService.setProjectPlanEndDate(projectPlan);
            projectService.addProjectPlanToProject(project, projectPlan);
        }
        projectService.setProjectEndDate(project);

        inOutService.outputFinalSchedule(project);
    }
}
