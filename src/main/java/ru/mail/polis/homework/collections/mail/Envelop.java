package ru.mail.polis.homework.collections.mail;

public class Envelop<T> {
    private final Client sender;
    private final Client recipient;
    private final T content;

    Envelop(Client sender, Client recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    Client getSender() {
        return sender;
    }

    Client getRecipient() {
        return recipient;
    }

    T getContent() {
        return content;
    }
}
