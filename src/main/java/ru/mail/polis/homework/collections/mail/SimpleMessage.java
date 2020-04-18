package ru.mail.polis.homework.collections.mail;

public class SimpleMessage<T> {
    private final String sender;
    private final String recipient;
    private final T content;

    public SimpleMessage(String sender, String recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public T getContent(){
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}
