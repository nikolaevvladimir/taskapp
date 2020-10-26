package com.task.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.app.TaskInfo;
import com.task.app.service.DataNotFoundException;
import com.task.app.service.TaskApiService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@SpringBootTest(classes={com.task.app.Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskApiService taskApiService;

    @Test
    public void getListTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/list-tasks").queryParam("skip", "0")
                .queryParam("limit", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void createTask() throws Exception {
        taskApiService.resetData();

        List<TaskInfo> items =  taskApiService.findData(0, 10);
        assertThat(items.size()).isEqualTo(0);

        mvc.perform(MockMvcRequestBuilders.post("/create-task").queryParam("name", "test")
                .queryParam("desc", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //                .andExpect(content().string(equalTo("")));

        items =  taskApiService.findData(0, 10);
        assertThat(items.size()).isEqualTo(1);
    }
}