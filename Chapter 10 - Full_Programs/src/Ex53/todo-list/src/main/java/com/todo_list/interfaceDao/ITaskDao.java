package com.todo_list.interfaceDao;

import java.util.Map;
import com.todo_list.model.Task;

public interface ITaskDao {
    void saveTask(Task task);
    Task getOneTask(Integer id);
    void updateTask(Task task);
    Map<Integer, Task> getAllTasks();
    void deleteTask(Integer id);
    void saveAllTasks(Map<Integer, Task> map);
}
