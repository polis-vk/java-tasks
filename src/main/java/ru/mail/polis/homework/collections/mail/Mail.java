package ru.mail.polis.homework.collections.mail;

public class Mail {
    private final String sender;
    private final String receiver;


    public Mail(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }
}
