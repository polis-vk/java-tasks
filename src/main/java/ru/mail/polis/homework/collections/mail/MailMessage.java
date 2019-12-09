package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Message {
    private String message;

    public MailMessage(String message, String rec, String sen) {
        super(rec, sen);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
