package com.scheduling.service;

import com.scheduling.api.TaskService;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    @Override
    public Optional<Task> getTaskByTaskName(List<Task> tasks, String taskName) {
        return tasks.stream().filter(t -> taskName.equals(t.getTaskName())).findFirst();
    }

    @Override
    public String getTaskListChoices(ProjectPlan projectPlan) {
        return projectPlan.getTasks().stream()
                .map(a -> a.getTaskName()).collect(Collectors.joining(", "));
    }

    @Override
    public void computeStartAndEndDateOfTask(ProjectPlan projectPlan, Task task) {
        int noOfDaysToComplete = task.getNoOfDaysToComplete() - 1;
        if (projectPlan.getTasks().size() == 0 || task.getDependencyTasks().size() == 0) {
            // start date of this task is the start date of the project plan if there are no current tasks
            // or if this task has no dependency
            task.setStartDate(projectPlan.getStartDate());
        } else {
            Task latestDependencyTask = task.getDependencyTasks().stream()
                    .sorted(Comparator.comparing(Task::getEndDate, Comparator.nullsLast(Comparator.reverseOrder())))
                    .findFirst().get();
            // start date of this task is after the end date of the latest dependency task
            task.setStartDate(latestDependencyTask.getEndDate().plusDays(1));
        }
        task.setEndDate(task.getStartDate().plusDays(noOfDaysToComplete));
    }
}
