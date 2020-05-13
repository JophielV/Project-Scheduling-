package com.scheduling.api;

import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;

public interface ProjectService {

    void computeEndDateOfProject(Project project);

    void addProjectPlanToProject(Project project, ProjectPlan projectPlan);
}
