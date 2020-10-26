package com.task.app;

import com.task.app.service.TaskApiService;
import com.task.app.service.DataNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Task data api controller
 */
@RestController
public class TaskApiController {
    Logger logger = LoggerFactory.getLogger(TaskApiController.class);

    private final TaskApiService taskApiService;

    @Autowired
    public TaskApiController(TaskApiService taskApiService) {
        this.taskApiService = taskApiService;
    }

    @ApiOperation("Create a task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok",
                    response = TaskApiResult.class),
            @ApiResponse(code = 500, message = "Some server error")}
    )
    @RequestMapping(value = "/create-task", method = RequestMethod.POST)
    public ResponseEntity<TaskApiResult> createTask(
            @ApiParam(name = "name", value = "Task name", defaultValue = "")
            @RequestParam(name = "name")
                    String name,
            @ApiParam(name = "desc", value = "Task desc", defaultValue = "")
            @RequestParam(name = "desc")
                    String desc
    ) {
        TaskApiResult.Builder builder = TaskApiResult.newBuilder();
        if (name == null) {
            TaskApiResult taskApiResult = builder.setError("param name is required").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        }

        try {
            TaskInfo taskData = taskApiService.createData(name, desc);
            TaskApiResult taskApiResult = builder.setResult(taskData).build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            logger.error("error", e);
            TaskApiResult taskApiResult = builder.setError("Task was not created").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Get a task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = TaskApiResult.class),
            @ApiResponse(code = 500, message = "Some server error")}
    )
    @RequestMapping(value = "/get-task", method = RequestMethod.GET)
    public ResponseEntity<TaskApiResult> getTask(
            @ApiParam(name = "id", value = "Task id", defaultValue = "")
            @RequestParam(name = "id")
                    String id
    ) {
        TaskApiResult.Builder builder = TaskApiResult.newBuilder();
        if (id == null) {
            TaskApiResult taskApiResult = builder.setError("param id is required").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        }

        try {
            TaskInfo taskData = taskApiService.getData(id);
            TaskApiResult taskApiResult = builder.setResult(taskData).build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            logger.error("error", e);
            TaskApiResult taskApiResult = builder.setError("Task was not found").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Delete a task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = TaskApiResult.class),
            @ApiResponse(code = 500, message = "Some server error")}
    )
    @RequestMapping(value = "/remove-task", method = RequestMethod.GET)
    public ResponseEntity<TaskApiResult> removeData(
            @ApiParam(name = "id", value = "Task id", defaultValue = "")
            @RequestParam(name = "id")
                    String id
    ) {
        TaskApiResult.Builder builder = TaskApiResult.newBuilder();
        if (id == null) {
            TaskApiResult taskApiResult = builder.setError("param id is required").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        }

        try {
            TaskInfo taskData = taskApiService.removeData(id);
            TaskApiResult taskApiResult = builder.setResult(taskData).build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            logger.error("error", e);
            TaskApiResult taskApiResult = builder.setError("Task was not deleted").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("Change a task")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok",
                    response = TaskApiResult.class),
            @ApiResponse(code = 500, message = "Some server error")}
    )
    @RequestMapping(value = "/change-task", method = RequestMethod.POST)
    public ResponseEntity<TaskApiResult> createTask(
            @ApiParam(name = "id", value = "Task id", defaultValue = "")
            @RequestParam(name = "id")
                    String id,
            @ApiParam(name = "name", value = "Task name", defaultValue = "")
            @RequestParam(name = "name")
                    String name,
            @ApiParam(name = "desc", value = "Task desc", defaultValue = "")
            @RequestParam(name = "desc")
                    String desc
    ) {
        TaskApiResult.Builder builder = TaskApiResult.newBuilder();

        if (id == null) {
            TaskApiResult taskApiResult = builder.setError("param id is required").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        }

        if (name == null) {
            TaskApiResult taskApiResult = builder.setError("param name is required").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        }

        try {
            TaskInfo taskData = taskApiService.updateData(id, name, desc);
            TaskApiResult taskApiResult = builder.setResult(taskData).build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            logger.error("error", e);
            TaskApiResult taskApiResult = builder.setError("Task was not changed").build();
            return new ResponseEntity<>(taskApiResult, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation("List of tasks")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = TaskApiResult.class),
            @ApiResponse(code = 500, message = "Some server error")}
    )
    @RequestMapping(value = "/list-tasks", method = RequestMethod.GET)
    public ResponseEntity<TasksApiResult> tasksList(
            @ApiParam(name = "skip", value = "Skip records", defaultValue = "0")
            @RequestParam(name = "skip")
                    Integer skip,
            @ApiParam(name = "limit", value = "Limit records", defaultValue = "10")
            @RequestParam(name = "limit")
                    Integer limit
    ) {
        TasksApiResult.Builder builder = TasksApiResult.newBuilder();
        try {
            List<TaskInfo> items = taskApiService.findData(skip, limit);
            TasksApiResult tasksApiResult = builder.setResult(items).build();
            return new ResponseEntity<>(tasksApiResult, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}