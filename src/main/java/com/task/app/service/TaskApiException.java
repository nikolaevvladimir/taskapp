package com.task.app.service;

import java.io.IOException;

/**
 * @author nikolaevvs
 *
 */
public class TaskApiException extends Throwable {


    public TaskApiException() {
    }

    public TaskApiException(String message) {
        super(message);
    }

    public TaskApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskApiException(Throwable cause) {
        super(cause);
    }
}
