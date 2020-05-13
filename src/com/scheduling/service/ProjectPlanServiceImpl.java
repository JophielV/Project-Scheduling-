package com.scheduling.service;

import com.scheduling.api.ProjectPlanService;
import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.time.LocalDate;
import java.util.Comparator;

public class ProjectPlanServiceImpl implements ProjectPlanService {

    @Override
    public void setProjectPlanStartDate(Project project, ProjectPlan projectPlan) {
        if (project.getProjectPlans().size() > 0) {
            LocalDate prevProjPlanEndDate = project.getProjectPlans().get(project.getProjectPlans().size() - 1).getEndDate();
            projectPlan.setStartDate(prevProjPlanEndDate.plusDays(1));
        } else {
            projectPlan.setStartDate(project.getStartDate());
        }
    }

    @Override
    public void computeEndDateOfProjectPlan(ProjectPlan projectPlan) {
        Task mostRecentTask = projectPlan.getTasks().stream()
                .sorted(Comparator.comparing(Task::getEndDate, Comparator.nullsLast(Comparator.reverseOrder()))).findFirst().get();
        projectPlan.setEndDate(mostRecentTask.getEndDate());

        int totalCompletionDays = projectPlan.getTasks().stream().mapToInt(Task::getNoOfDaysToComplete).sum();
        projectPlan.setNoOfDaysToComplete(totalCompletionDays);
    }

    @Override
    public void addTaskToProjectPlan(ProjectPlan projectPlan, Task task) {
        projectPlan.getTasks().add(task);
    }
}
