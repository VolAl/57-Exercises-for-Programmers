package com.todolistapp.model;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private static int taskId;
    private String taskName;
    private boolean completed;

    public Task() {
        // This is to have sequential ids, in spite of the multiple calls in MainFragment (on line 75)
        int nextId = idGenerator.getAndIncrement();
        if(nextId > taskId + 1) {
            taskId++;
        } else {
            taskId = nextId;
        }
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        Task.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return (completed ? "[X] " : "[ ] ") +
                "taskId: " + taskId +
                " - " + taskName;
    }
}
