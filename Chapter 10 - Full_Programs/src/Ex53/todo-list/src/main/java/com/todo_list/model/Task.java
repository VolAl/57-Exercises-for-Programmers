package com.todo_list.model;

import lombok.*;

import java.io.Serializable;

@Data
public class Task  implements Serializable {

    private Integer taskId;
    private String taskDescription;
    private boolean completed;

    public Task(Integer taskId, String taskDescription, boolean completed) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.completed = completed;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
