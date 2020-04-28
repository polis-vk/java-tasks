package ru.mail.polis.homework.collections.mail;

public class Mail<T> {

    private final String addressee;
    private final String sender;
    private final T content;

    public Mail(String addressee, String sender, T content) {
        this.addressee = addressee;
        this.sender = sender;
        this.content = content;
    }

    public String getAddressee() {
        return addressee;
    }

    public String getSender() {
        return sender;
    }

}
