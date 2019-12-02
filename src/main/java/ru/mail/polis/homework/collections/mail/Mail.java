package ru.mail.polis.homework.collections.mail;

public abstract class Mail<T> {
    private String sender, receiver;
    private T message;

    public Mail(String sender, String receiver, T message) {
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

    public T getMessage() {
        return message;
    }
}
