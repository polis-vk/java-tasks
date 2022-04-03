package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage<T> extends Mail {
    private final T message;

    public MailMessage(String recipient, String sender, T message) {
        super(recipient, sender);
        this.message = message;
    }

    public T getMessage() {
        return message;
    }
}
