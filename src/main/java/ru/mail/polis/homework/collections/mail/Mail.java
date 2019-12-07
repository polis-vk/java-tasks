package ru.mail.polis.homework.collections.mail;

@SuppressWarnings("WeakerAccess")
public class Mail {
    private String sender;
    private String receiver;

    protected Mail(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    protected String getSender() {
        return sender;
    }

    protected String getReceiver() {
        return receiver;
    }
}