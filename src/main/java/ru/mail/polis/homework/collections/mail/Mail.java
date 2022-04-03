package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String receiver;
    private final String sender;
    private final T content;

    public Mail(String receiver, String sender, T textOrValue) {
        this.content = textOrValue;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public T getContent() {
        return content;
    }
}
