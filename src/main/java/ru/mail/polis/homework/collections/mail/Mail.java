package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {

    private final String recipient;
    private final String sender;
    private final T massageContent;

    public Mail(String recipient, String sender, T massageContent) {
        this.recipient = recipient;
        this.sender = sender;
        this.massageContent = massageContent;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public T getMassageContent() {
        return massageContent;
    }
}
