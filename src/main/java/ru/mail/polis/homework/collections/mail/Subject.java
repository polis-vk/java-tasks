package ru.mail.polis.homework.collections.mail;

public abstract class Subject<T> {
    final private String sender_;
    final private String receiver_;
    final private T body_;

    public Subject(String sender, String receiver, T body) {
        this.sender_ = sender;
        this.receiver_ = receiver;
        this.body_ = body;
    }


    public String getSender_() {
        return sender_;
    }

    public String getReceiver_() {
        return receiver_;
    }

    public T getBody_() {
        return body_;
    }
}
