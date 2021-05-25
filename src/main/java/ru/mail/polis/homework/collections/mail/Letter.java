package ru.mail.polis.homework.collections.mail;

public abstract class Letter<T> {
    private String recipient;
    private String sender;
    private T content;

    public Letter(String recipient, String sender, T content) {
        this.recipient = recipient;
        this.sender = sender;
        this.content = content;
    }

    final public T getContent() {
        return content;
    }

    final public String getRecipient() {
        return recipient;
    }

    final public String getSender() {
        return sender;
    }

    final public void setSender(String sender) {
        this.sender = sender;
    }
}
