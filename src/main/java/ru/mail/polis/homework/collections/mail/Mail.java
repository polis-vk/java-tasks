package ru.mail.polis.homework.collections.mail;

public class Mail<T> {
    private final String sender;
    private final String receiver;
    private final T contents;

    public Mail(String sender, String receiver, T message) {
        this.sender = sender;
        this.receiver = receiver;
        this.contents = message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public T getContents() {
        return contents;
    }
}
