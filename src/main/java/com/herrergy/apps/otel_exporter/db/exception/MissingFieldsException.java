package com.herrergy.apps.otel_exporter.db.exception;

public class MissingFieldsException extends Exception {

    public MissingFieldsException(String message) {
        super(message);
    }
}
