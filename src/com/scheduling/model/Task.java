package com.scheduling.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private String taskName;
    private List<Task> preRequisiteTasks = new ArrayList<>();;
    private int noOfDaysToComplete;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<Task> getPreRequisiteTasks() {
        return preRequisiteTasks;
    }

    public void setPreRequisiteTasks(List<Task> preRequisiteTasks) {
        this.preRequisiteTasks = preRequisiteTasks;
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
