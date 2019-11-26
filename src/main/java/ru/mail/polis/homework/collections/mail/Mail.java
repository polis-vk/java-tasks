package ru.mail.polis.homework.collections.mail;

public abstract class Mail {

    private String receiver;
    private String sender;

    public Mail(String receiver, String sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
