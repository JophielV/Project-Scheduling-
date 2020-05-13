package com.scheduling.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String projectName;
    private List<ProjectPlan> projectPlans = new ArrayList<>();;
    private int noOfDaysToComplete;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectPlan> getProjectPlans() {
        return projectPlans;
    }

    public void setProjectPlans(List<ProjectPlan> projectPlans) {
        this.projectPlans = projectPlans;
    }

    public int getNoOfDaysToComplete() {
        return noOfDaysToComplete;
    }

    public void setNoOfDaysToComplete(int noOfDaysToComplete) {
        this.noOfDaysToComplete = noOfDaysToComplete;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
