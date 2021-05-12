package ru.mail.polis.homework.collections.mail;

public abstract class BaseMail<I> {
    private final String sender;
    private final String recipient;
    private final I item;

    public BaseMail(String sender, String recipient, I item) {
        this.sender = sender;
        this.recipient = recipient;
        this.item = item;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public I getItem() {
        return item;
    }
}
