package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final MailMessage<T> message;

    protected Mail(String recipient, String sender, T message) {
        this.recipient = recipient;
        this.sender = sender;
        this.message = new MailMessage<>(message);
    }

    public T getMessage() {
        return message.getMessage();
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }
}
