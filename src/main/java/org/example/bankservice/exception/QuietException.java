package org.example.bankservice.exception;

public class QuietException extends RuntimeException {

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
