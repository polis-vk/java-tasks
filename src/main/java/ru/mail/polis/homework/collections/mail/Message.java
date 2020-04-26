package ru.mail.polis.homework.collections.mail;

public class Message<T> {
    private final String sender;
    private final String recipient;
    private final T massageContent;

    public Message(String sender, String receiver, T massageContent) {
        this.sender = sender;
        this.recipient = receiver;
        this.massageContent = massageContent;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getMassageContent() {
        return massageContent;
    }
}
