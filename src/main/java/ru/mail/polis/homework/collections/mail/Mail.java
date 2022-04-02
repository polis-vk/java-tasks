package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String addressee;
    private final String sender;
    private final MailMessage<T> message;

    public Mail(String addressee, String sender, MailMessage<T> message) {
        this.addressee = addressee;
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getAddressee() {
        return addressee;
    }
}
