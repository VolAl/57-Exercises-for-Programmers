package com.todo_list.repo;

import com.todo_list.interfaceDao.ITaskDao;
import com.todo_list.model.Task;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Repository
public class TaskDaoImpl implements ITaskDao {

    private final String hashReference= "Task";

    @Resource(name="redisTemplate")
    private HashOperations<String, Integer, Task> hashOperations;

    @Override
    public void saveTask(Task task) {
        hashOperations.putIfAbsent(hashReference, task.getTaskId(), task);
    }

    @Override
    public void saveAllTasks(Map<Integer, Task> map) {
        hashOperations.putAll(hashReference, map);
    }

    @Override
    public Task getOneTask(Integer id) {
        return hashOperations.get(hashReference, id);
    }

    @Override
    public void updateTask(Task task) {
        hashOperations.put(hashReference, task.getTaskId(), task);
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        Map<Integer, Task> map = hashOperations.entries(hashReference);

        List<Map.Entry<Integer, Task>> list = new LinkedList<>(map.entrySet());
        list.sort(Comparator.comparing(t -> t.getValue().getTaskId()));

        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    @Override
    public void deleteTask(Integer id) {
        hashOperations.delete(hashReference, id);
    }
}
