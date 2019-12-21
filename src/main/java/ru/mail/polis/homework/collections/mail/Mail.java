package ru.mail.polis.homework.collections.mail;

public class Mail<T>{
    private String sender;
    private String receiver;
    private T text;

    Mail(String sender, String receiver, T text) {
        this.receiver = receiver;
        this.sender = sender;
        this.text = text;
    }

    public T getText() {
        return text;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
