package com.task.app.cache;

import com.task.app.TaskInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @author nikolaevvs
 * Simple cache with saving data in memory
 */
@Component
public class TaskRemDataCache implements TaskDataCache {

    private Map<String, TaskInfo> taskDataMap;

    public TaskRemDataCache() {
        this.taskDataMap = new ConcurrentHashMap<>();
    }

    @Override
    public TaskInfo createData(String name, String desc) {
        TaskInfo cache = new TaskInfo();
        cache.setId(UUID.randomUUID().toString());
        cache.setDesc(desc);
        cache.setName(name);
        cache.setDate(LocalDate.now());
        taskDataMap.put(cache.getId(), cache);
        return cache;
    }

    @Override
    public TaskInfo removeData(String id) {
        TaskInfo cache = taskDataMap.remove(id);
        return cache;
    }

    @Override
    public TaskInfo getData(String id) {
        TaskInfo cache = taskDataMap.get(id);
        return cache;
    }

    @Override
    public TaskInfo updateData(String id, String name, String desc) {
        TaskInfo cache = taskDataMap.get(id);
        if (cache != null) {
            cache.setDesc(desc);
            cache.setName(name);
            cache.setDate(LocalDate.now());
        }

        return cache;
    }

    @Override
    public List<TaskInfo> findData(int skip, int limit) {
        List<TaskInfo> collect = taskDataMap.values().stream()
                .skip(skip)
                .limit(limit)
                .sorted(new Comparator<TaskInfo>() {
                    @Override
                    public int compare(TaskInfo o1, TaskInfo o2) {
                        return o1.compareTo(o2);
                    }
                }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void resetData() {
        taskDataMap.clear();
    }
}
