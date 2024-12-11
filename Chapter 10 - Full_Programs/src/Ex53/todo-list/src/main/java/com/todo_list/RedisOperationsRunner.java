package com.todo_list;

import com.todo_list.interfaceDao.ITaskDao;
import com.todo_list.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.System.in;

@Component
public class RedisOperationsRunner implements CommandLineRunner {

    private static final Pattern isNumericPattern = Pattern.compile("^\\d*$");

    @Autowired
    private ITaskDao taskDao;

    @Override
    public void run(String... args) throws Exception {

        Map<Integer, Task> tasks = new HashMap<>();
        Map<Integer, Task> retrievedTasks = taskDao.getAllTasks();
        int taskIndex = 0;
        for(Task t : retrievedTasks.values()) {
            if(taskIndex < t.getTaskId()) {
                taskIndex = t.getTaskId();
            }
        }
        Scanner sc = new Scanner(in);
        String task;

        System.out.println("Welcome to your Todo List! Add as many chores/tasks you would like. To stop press the enter key.");
        do{
            System.out.print("Enter a chore or a task: ");
            task = sc.nextLine();
            if(!task.isEmpty()) {
                taskIndex++;
                tasks.put(taskIndex, new Task(taskIndex, task, false));
            }
        } while(!task.isEmpty());

        taskDao.saveAllTasks(tasks);

        retrievedTasks = taskDao.getAllTasks();
        printTasks(retrievedTasks);

        System.out.print("\nWould you like to complete a task?\nIf not press enter, otherwise enter the task ID: ");
        String completeTask = sc.nextLine();
        while(!completeTask.isEmpty() && isNumericPattern.matcher(completeTask).matches()){
            Task taskToComplete = taskDao.getOneTask(Integer.parseInt(completeTask));
            if(taskToComplete.isCompleted()) {
                System.out.println("Task already completed!");
            } else {
                taskToComplete.setCompleted(true);
                taskDao.updateTask(taskToComplete);
                System.out.println("Task " + completeTask + " completed!");
            }
            System.out.print("Would you like to complete another task? ");
            completeTask = sc.nextLine();
        }

        retrievedTasks = taskDao.getAllTasks();
        printTasks(retrievedTasks);

        sc.close();

        System.exit(0);
    }

    private void printTasks(Map<Integer, Task> tasks) {
        for(Map.Entry<Integer,Task> entry : tasks.entrySet()) {
            Task t = entry.getValue();
            System.out.println((t.isCompleted() ? "[X] - " : "[ ] - ") +
                               "task ID: " + t.getTaskId() + ", " +
                               t.getTaskDescription());
        }
    }
}
