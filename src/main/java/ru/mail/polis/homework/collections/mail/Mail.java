package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private String sender;
    private String addressee;
    private T message;

    public Mail(String sender, String addressee, T message) {
        this.sender = sender;
        this.addressee = addressee;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getAddressee() {
        return addressee;
    }

    public T getMessage() {
        return message;
    }

}
