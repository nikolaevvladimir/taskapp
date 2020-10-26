package com.task.app.service;

import com.task.app.TaskInfo;
import com.task.app.cache.TaskDataCache;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nikolaevvs
 * @version $
 */
@Service
public class TaskApiService {
    Logger logger = LoggerFactory.getLogger(TaskApiService.class);

    private final TaskDataCache taskDataCache;

    private ReentrantLock mutex = new ReentrantLock();

    @Autowired
    public TaskApiService(TaskDataCache taskDataCache) {
        this.taskDataCache = taskDataCache;
    }

    public TaskInfo createData(String name, String desc) throws DataNotFoundException {
        try {
            mutex.lock();
            TaskInfo cache = taskDataCache.createData(name, desc);
            if (cache == null) {
                throw new DataNotFoundException();
            }
            return cache;
        } finally {
            mutex.unlock();
        }
    }

    public TaskInfo removeData(String id) throws DataNotFoundException {
        try {
            mutex.lock();
            TaskInfo cache = taskDataCache.removeData(id);
            if (cache == null) {
                throw new DataNotFoundException();
            }
            return cache;
        } finally {
            mutex.unlock();
        }
    }

    public TaskInfo getData(String id) throws DataNotFoundException {
        TaskInfo cache = taskDataCache.getData(id);

        if (cache == null) {
            throw new DataNotFoundException();
        }

        return cache;
    }

    public TaskInfo updateData(String id, String name, String desc) throws DataNotFoundException {
        try {
            mutex.lock();
            TaskInfo cache = taskDataCache.updateData(id, name, desc);
            if (cache == null) {
                throw new DataNotFoundException();
            }
            return cache;
        } finally {
            mutex.unlock();
        }
    }

    public List<TaskInfo> findData(int skip, int limit) {
        List<TaskInfo> items = taskDataCache.findData(skip, limit);
        return items;
    }

    public void resetData() {
        try {
            mutex.lock();
            taskDataCache.resetData();
        } finally {
            mutex.unlock();
        }
    }
}