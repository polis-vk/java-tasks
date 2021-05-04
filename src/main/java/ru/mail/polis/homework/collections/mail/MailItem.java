package ru.mail.polis.homework.collections.mail;

public class MailItem<T> {
    String sender;
    String receiver;
    T entry;
    public String getSender() {
        return sender;
    }

    public MailItem(String sender, String receiver, T entry) {
        this.sender = sender;
        this.receiver = receiver;
        this.entry = entry;
    }
}
