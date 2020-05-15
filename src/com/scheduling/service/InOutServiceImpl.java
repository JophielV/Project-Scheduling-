package com.scheduling.service;

import com.scheduling.api.InOutService;
import com.scheduling.api.TaskService;
import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InOutServiceImpl implements InOutService {

    private Scanner sc = new Scanner(System.in);
    private TaskService taskService = new TaskServiceImpl();
    private static final String DATE_INPUT_FORMAT = "yyyy-MM-dd";

    @Override
    public void getProjectNameInput(Project project) {
        LocalDate projectStartDate = LocalDate.now();
        String projectName = getStringOfInput("Enter project name: ");
        project.setProjectName(projectName);
        project.setStartDate(projectStartDate);
    }

    @Override
    public int getNoOfProjectPlansInput() {
        return getNumberOfInput("Enter number of project plans: ", false);
    }

    @Override
    public void getProjectPlanNameInput(ProjectPlan projectPlan, int index) {
        String projectPlanName = getStringOfInput("Enter project plan (" + (index + 1) +  ") name: ");
        projectPlan.setPlanName(projectPlanName);
    }

    @Override
    public void getProjectPlanStartDateInput(ProjectPlan projectPlan) {
        boolean isValid = false;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_INPUT_FORMAT);
        LocalDate convertedDate = null;
        do {
            String startDate = getStringOfInput("Enter start date for this project plan (yyyy-mm-dd): ");
            if (isValidDate(startDate)) {
                convertedDate = LocalDate.parse(startDate, dateFormatter);
                if (!convertedDate.isBefore(LocalDate.now())) {
                    isValid = true;
                } else {
                    System.out.println("Warning: Past dates not valid!");
                }
            } else {
                System.out.println("Warning: Invalid Date!");
            }
        } while(!isValid);
        projectPlan.setStartDate(convertedDate);
    }

    @Override
    public int getNoOfTasksInput() {
        return getNumberOfInput("Enter number of tasks for this plan: ", false);
    }

    @Override
    public void getTaskNameInput(ProjectPlan projectPlan, Task task, int index) {
        boolean validTaskName = false;
        String taskName;
        do {
            taskName = getStringOfInput("Enter task (" + (index + 1) + ") name: ");
            Optional<Task> dependencyTask = taskService.getTaskByTaskName(projectPlan.getTasks(), taskName);
            if (dependencyTask.isPresent()) {
                System.out.println("Warning: Task name already exists for this plan!");
                validTaskName = false;
            } else {
                validTaskName = true;
            }
        } while(!validTaskName);

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
            int noOfDependencies;
            do {
                noOfDependencies = getNumberOfInput("How many are dependencies of this task? (Current number of added tasks for this plan: " +
                        projectPlan.getTasks().size() + "): ", true);
                if (noOfDependencies > projectPlan.getTasks().size()) {
                    System.out.println("Warning: Must not exceed the current number of added tasks!");
                }
            } while (noOfDependencies > projectPlan.getTasks().size());

            for (int k = 0; k < noOfDependencies; k++) {
                System.out.println("Added tasks for this plan: " + taskService.getTaskListChoices(projectPlan));

                boolean validTask = false;
                do {
                    String dependencyTaskName = getStringOfInput("Enter the name of dependency task (" + (k + 1) + "): ");
                    Optional<Task> dependencyTask = taskService.getTaskByTaskName(projectPlan.getTasks(), dependencyTaskName);
                    if (dependencyTask.isPresent() &&
                            task.getDependencyTasks().stream().filter(p -> dependencyTaskName.equals(p.getTaskName())).count() == 0) {
                        task.getDependencyTasks().add(dependencyTask.get());
                        validTask = true;
                    } else {
                        System.out.println("Warning: Task name does not exist or already added as dependency to this task!");
                        validTask = false;
                    }
                } while(!validTask);
            }
        }
        System.out.println();
    }

    @Override
    public void outputProjectPlanSchedule(ProjectPlan projectPlan) {
        System.out.println("********************** PROJECT PLAN SUMMARY **********************");
        outputTasksDetails(projectPlan);
        System.out.println("********************** END OF PROJECT PLAN SUMMARY **********************");
        System.out.println();
    }

    private void outputTasksDetails(ProjectPlan projectPlan) {
        System.out.println("*********** Project Plan: " + projectPlan.getPlanName());
        System.out.println("*********** Project Plan Start date: " + projectPlan.getStartDate());
        for (Task task : projectPlan.getTasks())  {
            System.out.println("**** Task: " + task.getTaskName()  + ", Days to complete: " + task.getNoOfDaysToComplete() + ", Start Date: " + task.getStartDate() + ", End Date: " + task.getEndDate());
            if (task.getDependencyTasks().size() > 0) {
                System.out.println("******** Dependency tasks: " + task.getDependencyTasks().stream().map(a -> a.getTaskName()).collect(Collectors.joining(", ")));
            }
        }
        System.out.println("*********** Project Plan: " + " Days to complete: " + projectPlan.getNoOfDaysToComplete() + ", Start Date: " + projectPlan.getStartDate()  + ", End Date: " + projectPlan.getEndDate());
        System.out.println();
    }

    @Override
    public void outputOverallProjectSchedule(Project project) {
        System.out.println("********************** OVERALL PROJECT SCHEDULE OUTPUT **********************");
        System.out.println(" Project: " + project.getProjectName());
        System.out.println();

        for (ProjectPlan projectPlan: project.getProjectPlans()) {
            outputTasksDetails(projectPlan);
        }
        System.out.println("Project: " + " Days to complete: " + project.getNoOfDaysToComplete()  + ", Start Date: " + project.getStartDate() + ", End Date: " + project.getEndDate());
        System.out.println("********************** END OF OVERALL PROJECT SCHEDULE OUTPUT **********************");
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

    private String getStringOfInput(String message) {
        String input = null;
        do {
            System.out.print(message);
            input = sc.nextLine();
        } while (input == null || input.isEmpty());

        return input;
    }

    public boolean isValidDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(DATE_INPUT_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
