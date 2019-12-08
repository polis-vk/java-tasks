package ru.mail.polis.homework.collections.mail;

public class MailMessage implements Mail {

    private String sender;
    private String receiver;
    private String message;

    public MailMessage(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMailMessage() {
        return message;
    }
}
