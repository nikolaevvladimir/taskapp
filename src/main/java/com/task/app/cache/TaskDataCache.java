package com.task.app.cache;

import com.task.app.TaskInfo;

import java.time.LocalDate;
import java.util.List;

/**
 * @author nikolaevvs
 * Cache interface
 */
public interface TaskDataCache {
    TaskInfo createData(String name, String desc);

    TaskInfo removeData(String id);

    TaskInfo getData(String id);

    TaskInfo updateData(String id, String name, String desc);

    List<TaskInfo> findData(int skip, int limit);

    void resetData();
}