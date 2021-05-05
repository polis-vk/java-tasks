package ru.mail.polis.homework.collections.mail;

public abstract class MessagingUnit<T> {
    private final String receiver;
    private final String sender;
    private final T content;

    MessagingUnit(String receiver, String sender, T content){
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public T getContent() {
        return content;
    }
}
