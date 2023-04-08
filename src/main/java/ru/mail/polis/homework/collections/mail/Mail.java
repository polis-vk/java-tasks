package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String receiver;
    private final String sender;
    private final T information;

    public Mail(String receiver, String sender, T information) {
        this.receiver = receiver;
        this.sender = sender;
        this.information = information;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getSender() {
        return this.sender;
    }

    public T getInformation() {
        return this.information;
    }
}
