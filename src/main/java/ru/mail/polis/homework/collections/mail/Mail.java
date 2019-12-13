package ru.mail.polis.homework.collections.mail;

public class Mail<T> {

    private final String sender;
    private final String addressee;
    private final T message;

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
