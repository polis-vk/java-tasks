package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {
    public MailMessage(String sender, String recipient, String message) {
        super(recipient, sender);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private final String message;
}
