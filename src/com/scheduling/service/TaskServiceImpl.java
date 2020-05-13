package com.scheduling.service;

import com.scheduling.api.TaskService;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    @Override
    public Task getTaskByTaskName(List<Task> tasks, String taskName) {
        Optional<Task> task =  tasks.stream().filter(t -> taskName.equals(t.getTaskName())).findFirst();
        return task.get();
    }

    @Override
    public void computeStartAndEndDateOfTask(ProjectPlan projectPlan, Task task) {
        int noOfDaysToComplete = task.getNoOfDaysToComplete() - 1;
        if (projectPlan.getTasks().size() == 0 || task.getPreRequisiteTasks().size() == 0) {
            task.setStartDate(projectPlan.getStartDate());
        } else {
            Task mostRecentTask = task.getPreRequisiteTasks().stream()
                    .sorted(Comparator.comparing(Task::getEndDate, Comparator.nullsLast(Comparator.reverseOrder()))).findFirst().get();
            task.setStartDate(mostRecentTask.getEndDate().plusDays(1));
        }
        task.setEndDate(task.getStartDate().plusDays(noOfDaysToComplete));
    }
}
