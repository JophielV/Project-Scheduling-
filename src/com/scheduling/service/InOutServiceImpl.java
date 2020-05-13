package com.scheduling.service;

import com.scheduling.api.InOutService;
import com.scheduling.api.TaskService;
import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.time.LocalDate;
import java.util.Scanner;

public class InOutServiceImpl implements InOutService {

    private Scanner sc = new Scanner(System.in);
    private TaskService taskService = new TaskServiceImpl();

    @Override
    public void getProjectNameInput(Project project) {
        LocalDate projectStartDate = LocalDate.now();
        System.out.print("Enter project name: ");
        String projectName = sc.nextLine();
        project.setProjectName(projectName);
        project.setStartDate(projectStartDate);
    }

    @Override
    public int getNoOfProjectPlansInput() {
        return getNumberOfInput("Enter number of project plans: ", false);
    }

    @Override
    public void getProjectPlanNameInput(ProjectPlan projectPlan, int index) {
        System.out.print("Enter project plan (" + (index + 1) +  ") name: " );
        String projectPlanName = sc.nextLine();
        projectPlan.setPlanName(projectPlanName);
    }

    @Override
    public int getNoOfTasksInput() {
        return getNumberOfInput("Enter number of tasks for this plan: ", false);
    }

    @Override
    public void getTaskNameInput(Task task, int index) {
        System.out.print("Enter task (" + (index + 1) + ") name: " );
        String taskName = sc.nextLine();
        task.setTaskName(taskName);
    }

    @Override
    public int getNoOfDaysToCompleteTask(Task task) {
        int noOfDaysToComplete = getNumberOfInput("Enter number of days to complete this task: ", false);
        task.setNoOfDaysToComplete(noOfDaysToComplete);
        return noOfDaysToComplete;
    }

    @Override
    public void getTaskDependenciesInput(ProjectPlan projectPlan, Task task) {
        if (projectPlan.getTasks().size() > 0) {
            int noOfDependencies =  getNumberOfInput("How many are dependencies of this task?: ", true);
            for (int k = 0; k < noOfDependencies; k++) {
                System.out.println("Task list for this plan: " + taskService.getTaskListChoices(projectPlan));
                System.out.print("Enter the name of dependency task (" + (k+1) + "): ");
                String dependencyTaskName = sc.nextLine();
                Task dependencyTask = taskService.getTaskByTaskName(projectPlan.getTasks(), dependencyTaskName);
                task.getPreRequisiteTasks().add(dependencyTask);
            }
        }
        System.out.println();
    }

    @Override
    public void outputFinalSchedule(Project project) {
        System.out.println("********************** SCHEDULE OUTPUT **********************");
        System.out.println(" Project: " + project.getProjectName());
        System.out.println();

        for (ProjectPlan projectPlan: project.getProjectPlans()) {
            System.out.println("*********** Project Plan: " + projectPlan.getPlanName());
            for (Task task : projectPlan.getTasks())  {
                System.out.println("**** Task: " + task.getTaskName()  + ", Days to complete: " + task.getNoOfDaysToComplete() + ", Start Date: " + task.getStartDate() + ", End Date: " + task.getEndDate());
            }
            System.out.println("*********** Project Plan: " + " Days to complete: " + projectPlan.getNoOfDaysToComplete() + ", Start Date: " + projectPlan.getStartDate()  + ", End Date: " + projectPlan.getEndDate());
            System.out.println();
        }
        System.out.println("Project: " + " Days to complete: " + project.getNoOfDaysToComplete()  + ", Start Date: " + project.getStartDate() + ", End Date: " + project.getEndDate());
    }

    @Override
    public void outputCreateProjectPlanNotice() {
        System.out.println();
        System.out.println("********************** Creating Project Plans **********************");
        System.out.println();
    }

    @Override
    public void outputCreateTaskNotice(ProjectPlan projectPlan) {
        System.out.println();
        System.out.println("******** Creating tasks for Project Plan: " + projectPlan.getPlanName() + " ********");
        System.out.println();
    }

    @Override
    public void outputEndTaskCreationNotice(ProjectPlan projectPlan) {
        System.out.println("********************** End of Creating tasks for Project Plan: " + projectPlan.getPlanName() + " **********************");
        System.out.println();
    }

    private int getNumberOfInput(String message, boolean isZeroAllowed) {
        int number = 0;
        do {
            System.out.print(message);
            while (!sc.hasNextInt()) {
                System.out.print("Please enter a valid number!: ");
                sc.next();
            }
            number = sc.nextInt();
        } while ((number <= -1 && isZeroAllowed) || (number <= 0 && !isZeroAllowed));
        sc.nextLine();

        return number;
    }
}
