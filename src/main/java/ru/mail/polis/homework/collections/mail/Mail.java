package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String addressee;
    private final String sender;
    private final T information;

    public Mail(String addressee, String sender, T information) {
        this.addressee = addressee;
        this.sender = sender;
        this.information = information;
    }

    public String getAddressee() {
        return this.addressee;
    }

    public String getSender() {
        return this.sender;
    }

    public T getInformation() {
        return this.information;
    }
}
