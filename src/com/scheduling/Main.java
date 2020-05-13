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

        System.out.println("Creating project plans");

        for (int i = 0; i < projectPlansNo; i++) {
            ProjectPlan projectPlan = new ProjectPlan();
            System.out.print("Enter project plan name: " );
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

            System.out.println("Creating tasks for this plan");
            for (int j = 0; j < noOfTasks; j++) {
                Task task = new Task();
                System.out.print("Enter task name: " );
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
                        System.out.print("Enter the name of dependency task (" + k + "): ");
                        String dependencyTaskName = sc.nextLine();
                        Task dependencyTask = getTask(projectPlan.getTasks(), dependencyTaskName);
                        task.getPreRequisiteTasks().add(dependencyTask);
                    }
                } else {
                    sc.nextLine();
                }
                computeStartAndEndDateOfTask(projectPlan, task);
                projectPlan.getTasks().add(task);
            }
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
                System.out.println("**** Task: " + task.getTaskName() + ", startDate: " + task.getStartDate() + ", endDate: " + task.getEndDate());
            }
            System.out.println("*********** Project Plan: StartDate: " + projectPlan.getStartDate() + ", EndDate: " + projectPlan.getEndDate());
            System.out.println();
        }
        System.out.println("Project: StartDate: " + project.getStartDate() + ", EndDate: " + project.getEndDate());

    }

    private static Task getTask(List<Task> tasks, String taskName) {
        Optional<Task> task =  tasks.stream().filter(t -> taskName.equals(t.getTaskName())).findFirst();
        return task.get();
    }

    private static void computeStartAndEndDateOfTask(ProjectPlan projectPlan, Task task) {
        int noOfDaysToComplete = task.getNoOfDaysToComplete();
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
    }

    private static void computeEndDateOfProject(Project project) {
        ProjectPlan latestProjectPlan = project.getProjectPlans().stream()
                .sorted(Comparator.comparing(ProjectPlan::getEndDate, Comparator.nullsLast(Comparator.reverseOrder()))).findFirst().get();
        project.setEndDate(latestProjectPlan.getEndDate());
    }
}
