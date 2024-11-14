package com.yan.TrilhaBackEndNov.service.exception;

public class DuplicateTaskException extends RuntimeException {

    public DuplicateTaskException(String message) {
        super(message);
    }

    public DuplicateTaskException() {
        super("Tarefa já existe");
    }
}
