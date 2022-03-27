package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage<T> {
    private final String sender;
    private final String recipient;
    private final T message;

    public MailMessage(String sender, String recipient, T message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getMessage() {
        return message;
    }
}
