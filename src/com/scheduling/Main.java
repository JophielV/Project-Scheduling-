package com.scheduling;

import com.scheduling.model.Project;
import com.scheduling.model.ProjectPlan;
import com.scheduling.model.Task;

import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Project project = new Project();
        LocalDate projectStartDate = LocalDate.now();

        System.out.print("Enter project name: ");
        String projectName = sc.nextLine();
        project.setProjectName(projectName);
        project.setStartDate(projectStartDate);

        System.out.print("Enter number of project plans: ");
        int projectPlansNo = sc.nextInt();
        sc.nextLine();

        System.out.println();
        System.out.println("********************** Creating Project Plans **********************");
        System.out.println();

        for (int i = 0; i < projectPlansNo; i++) {
            ProjectPlan projectPlan = new ProjectPlan();
            System.out.print("Enter project plan (" + (i+1) +  ") name: " );
            String projectPlanName = sc.nextLine();
            projectPlan.setPlanName(projectPlanName);

            if (project.getProjectPlans().size() > 0) {
                LocalDate prevProjPlanEndDate = project.getProjectPlans().get(project.getProjectPlans().size() - 1).getEndDate();
                projectPlan.setStartDate(prevProjPlanEndDate.plusDays(1));
            } else {
                projectPlan.setStartDate(project.getStartDate());
            }

            System.out.print("Enter number of tasks for this plan: ");
            int noOfTasks = sc.nextInt();
            sc.nextLine();

            System.out.println();
            System.out.println("******** Creating tasks for Project Plan: " + projectPlan.getPlanName() + " ********");
            System.out.println();

            for (int j = 0; j < noOfTasks; j++) {
                Task task = new Task();
                System.out.print("Enter task (" + (j+1) + ") name: " );
                String taskName = sc.nextLine();
                task.setTaskName(taskName);

                System.out.print("Enter number of days to complete this task: ");
                int noOfDaysToComplete = sc.nextInt();
                task.setNoOfDaysToComplete(noOfDaysToComplete);

                if (projectPlan.getTasks().size() > 0) {
                    System.out.print("How many are dependencies of this task?: ");
                    int noOfDependencies = sc.nextInt();
                    sc.nextLine();
                    for (int k = 0; k < noOfDependencies; k++) {
                        System.out.print("Enter the name of dependency task (" + (k+1) + "): ");
                        String dependencyTaskName = sc.nextLine();
                        Task dependencyTask = getTask(projectPlan.getTasks(), dependencyTaskName);
                        task.getPreRequisiteTasks().add(dependencyTask);
                    }
                } else {
                    sc.nextLine();
                }

                System.out.println();
                computeStartAndEndDateOfTask(projectPlan, task);
                projectPlan.getTasks().add(task);
            }
            System.out.println("********************** End of Creating tasks for Project Plan: " + projectPlan.getPlanName() + " **********************");
            System.out.println();
            computeEndDateOfProjectPlan(projectPlan);
            project.getProjectPlans().add(projectPlan);
        }
        computeEndDateOfProject(project);

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

    private static Task getTask(List<Task> tasks, String taskName) {
        Optional<Task> task =  tasks.stream().filter(t -> taskName.equals(t.getTaskName())).findFirst();
        return task.get();
    }

    private static void computeStartAndEndDateOfTask(ProjectPlan projectPlan, Task task) {
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

    private static void computeEndDateOfProjectPlan(ProjectPlan projectPlan) {
        Task mostRecentTask = projectPlan.getTasks().stream()
                .sorted(Comparator.comparing(Task::getEndDate, Comparator.nullsLast(Comparator.reverseOrder()))).findFirst().get();
        projectPlan.setEndDate(mostRecentTask.getEndDate());

        int totalCompletionDays = projectPlan.getTasks().stream().mapToInt(Task::getNoOfDaysToComplete).sum();
        projectPlan.setNoOfDaysToComplete(totalCompletionDays);
    }

    private static void computeEndDateOfProject(Project project) {
        ProjectPlan latestProjectPlan = project.getProjectPlans().stream()
                .sorted(Comparator.comparing(ProjectPlan::getEndDate, Comparator.nullsLast(Comparator.reverseOrder()))).findFirst().get();
        project.setEndDate(latestProjectPlan.getEndDate());

        int totalCompletionDays = project.getProjectPlans().stream().mapToInt(ProjectPlan::getNoOfDaysToComplete).sum();
        project.setNoOfDaysToComplete(totalCompletionDays);
    }
}
