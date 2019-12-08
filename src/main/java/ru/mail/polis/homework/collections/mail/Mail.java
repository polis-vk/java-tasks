package ru.mail.polis.homework.collections.mail;

@SuppressWarnings("WeakerAccess")
public class Mail {

    private final String sender;
    private final String receiver;

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