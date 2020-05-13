package com.scheduling.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectPlan {

    private String planName;
    private List<Task> tasks = new ArrayList<>();
    private int noOfDaysToComplete;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
