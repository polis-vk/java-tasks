package ru.mail.polis.homework.collections.mail;

public class MailItem<T> {
    private final String sender;
    private final String receiver;
    private final T entry;

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public T getEntry() {
        return entry;
    }

    public MailItem(String sender, String receiver, T entry) {
        this.sender = sender;
        this.receiver = receiver;
        this.entry = entry;
    }
}
