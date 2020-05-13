package com.scheduling.service;

import com.scheduling.api.ProjectService;
import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;

import java.util.Comparator;

public class ProjectServiceImpl implements ProjectService {

    @Override
    public void computeEndDateOfProject(Project project) {
        ProjectPlan latestProjectPlan = project.getProjectPlans().stream()
                .sorted(Comparator.comparing(ProjectPlan::getEndDate, Comparator.nullsLast(Comparator.reverseOrder()))).findFirst().get();
        project.setEndDate(latestProjectPlan.getEndDate());

        int totalCompletionDays = project.getProjectPlans().stream().mapToInt(ProjectPlan::getNoOfDaysToComplete).sum();
        project.setNoOfDaysToComplete(totalCompletionDays);
    }

    @Override
    public void addProjectPlanToProject(Project project, ProjectPlan projectPlan) {
        project.getProjectPlans().add(projectPlan);
    }
}
