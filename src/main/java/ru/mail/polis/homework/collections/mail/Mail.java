package ru.mail.polis.homework.collections.mail;

public class Mail<T> {
    private T messageContent;
    private String sender;
    private String receiver;

    public Mail(String sender, String receiver, T content) {
        this.messageContent = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public T getMessageContent() {
        return messageContent;
    }
}
