package ru.mail.polis.homework.collections.mail;


/**
 * 1 тугрик
 */
public abstract class Mail<T> {
    final private String sender;
    final private String recipient;
    final private T data;

    Mail(String sender, String consumer, T data) {
        this.sender = sender;
        this.recipient = consumer;
        this.data = data;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getData() {
        return data;
    }
}
