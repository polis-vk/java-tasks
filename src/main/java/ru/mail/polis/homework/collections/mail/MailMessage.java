package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage<T> {
    private final T message;

    public MailMessage(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }
}
