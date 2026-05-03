package com.program.appointment.exception;

public class EmailTakenException extends RuntimeException {

    public EmailTakenException(String message) {
        super(message);
    }
}
