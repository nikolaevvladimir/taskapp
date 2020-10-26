package com.task.app;

import java.util.List;
import io.swagger.annotations.ApiModelProperty;

public class TasksApiResult {
    @ApiModelProperty("Result Task data ")
    private List<TaskInfo> result;

    @ApiModelProperty("Message in case of error")
    private String error;

    @ApiModelProperty("Result status")
    private boolean success;

    public List<TaskInfo> getResult() {
        return result;
    }

    public void setResult(List<TaskInfo> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static TasksApiResult.Builder newBuilder() {
        return new TasksApiResult().new Builder();
    }

    class Builder{
        public TasksApiResult.Builder setSuccess(boolean success){
            TasksApiResult.this.success = success;
            return this;
        }

        public TasksApiResult.Builder setError(String error){
            TasksApiResult.this.error = error;
            TasksApiResult.this.success = false;
            return this;
        }

        public TasksApiResult.Builder setResult(List<TaskInfo> result){
            TasksApiResult.this.result = result;
            TasksApiResult.this.success = true;
            return this;
        }

        public TasksApiResult build(){
            return TasksApiResult.this;
        }
    }
}