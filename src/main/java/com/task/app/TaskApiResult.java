package com.task.app;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author nikolaevvs
 */
public class TaskApiResult {
    @ApiModelProperty("Result Task data ")
    private TaskInfo result;

    @ApiModelProperty("Message in case of error")
    private String error;

    @ApiModelProperty("Result status")
    private boolean success;

    public TaskInfo getResult() {
        return result;
    }

    public void setResult(TaskInfo result) {
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

    public static Builder newBuilder() {
        return new TaskApiResult().new Builder();
    }

     class Builder{
        public Builder setSuccess(boolean success){
            TaskApiResult.this.success = success;
            return this;
        }

        public Builder setError(String error){
            TaskApiResult.this.error = error;
            TaskApiResult.this.success = false;
            return this;
        }

        public Builder setResult(TaskInfo result){
            TaskApiResult.this.result = result;
            TaskApiResult.this.success = true;
            return this;
        }

        public TaskApiResult build(){
            return TaskApiResult.this;
        }
    }
}