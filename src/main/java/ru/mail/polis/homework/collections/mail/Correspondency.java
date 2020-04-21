package ru.mail.polis.homework.collections.mail;

public class Correspondency<T> {
    private final String sender;
    private final String receiver;
    private final T content;

    public Correspondency(String sender, String receiver, T content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public T getContent() {
        return content;
    }
}
