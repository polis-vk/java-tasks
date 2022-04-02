package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private String addressee;
    private String addresser;
    private T text;

    public Mail(String addressee, String addresser, T text) {
        this.addressee = addressee;
        this.addresser = addresser;
        this.text = text;
    }

    public String getAddressee() {
        return addressee;
    }

    public String getAddresser() {
        return addresser;
    }

    public T getText() {
        return text;
    }
}
