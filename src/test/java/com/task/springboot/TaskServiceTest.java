package com.task.springboot;

import com.task.app.TaskInfo;
import com.task.app.service.DataNotFoundException;
import com.task.app.service.TaskApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {com.task.app.Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskServiceTest {

    @Autowired
    private TaskApiService taskApiService;

    @Test
    public void createTaskTest() {
        try {
            TaskInfo taskInfo = taskApiService.createData("test", "test");
            assertThat(taskInfo.getId()).isNotBlank();
            assertThat(taskInfo.getName()).isEqualTo("test");
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            assertThat(true).isFalse();
        }
    }

    @Test
    public void updateTaskTest() {
        try {
            TaskInfo taskInfo = taskApiService.createData("test", "test");
            assertThat(taskInfo.getId()).isNotBlank();
            assertThat(taskInfo.getName()).isEqualTo("test");

            taskInfo = taskApiService.updateData(taskInfo.getId(), "test1", "test1");
            assertThat(taskInfo.getId()).isNotBlank();
            assertThat(taskInfo.getName()).isEqualTo("test1");
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            assertThat(true).isFalse();
        }
    }

    @Test
    public void removeTaskTest() {
        String id = "";
        try {
            TaskInfo taskInfo = taskApiService.createData("test", "test");
            assertThat(taskInfo.getId()).isNotBlank();
            assertThat(taskInfo.getName()).isEqualTo("test");
            id = taskInfo.getId();
            taskApiService.removeData(id);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            assertThat(true).isFalse();
        }

        try {
            taskApiService.getData(id);
        } catch (DataNotFoundException e) {
            assertThat(true).isTrue();
            return;
        }

        assertThat(true).isFalse();
    }

    @Test
    public void listTaskTest() {
        taskApiService.resetData();

        List<TaskInfo> items =  taskApiService.findData(0, 10);
        assertThat(items.size()).isEqualTo(0);

        try {
            TaskInfo taskInfo = taskApiService.createData("test", "test");
            assertThat(taskInfo.getId()).isNotBlank();
            assertThat(taskInfo.getName()).isEqualTo("test");
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            assertThat(true).isFalse();
        }

        items =  taskApiService.findData(0, 10);
        assertThat(items.size()).isEqualTo(1);
    }
}