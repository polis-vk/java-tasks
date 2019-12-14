package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {
    private final String message;

    public MailMessage(String sender, String recipient, String message) {
        super(sender, recipient);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}