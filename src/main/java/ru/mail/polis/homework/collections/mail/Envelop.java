package ru.mail.polis.homework.collections.mail;

class Envelop<T> {
    private final String sender;
    private final String recipient;
    private final T content;

    Envelop(String sender, String recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    String getSender() {
        return sender;
    }

    String getRecipient() {
        return recipient;
    }

    T getContent() {
        return content;
    }
}
