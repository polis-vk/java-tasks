package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String sender;
    private final String addressee;
    private final T messages;

    public Mail(String sender, String addressee, T message) {
        this.sender = sender;
        this.addressee = addressee;
        this.messages = message;
    }

    public String getSender() {
        return sender;
    }

    public String getAddressee() {
        return addressee;
    }

    public T getMessages() {
        return messages;
    }
}
