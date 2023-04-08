package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail {
    private final String message;

    public MailMessage(String recipient, String sender, String message) {
        super(recipient, sender);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
