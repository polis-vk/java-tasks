package ru.mail.polis.homework.io.blocking;

import java.io.IOException;

public class StructureInputException extends IOException {
    private static final String DEFAULT_MESSAGE = "File broken or hacker attack!";

    public StructureInputException() {
        super(DEFAULT_MESSAGE);
    }

    public StructureInputException(String message) {
        super(message);
    }

    public StructureInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public StructureInputException(Throwable cause) {
        super(cause);
    }
}
