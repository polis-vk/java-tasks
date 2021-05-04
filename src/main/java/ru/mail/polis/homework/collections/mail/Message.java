package ru.mail.polis.homework.collections.mail;

import java.util.Objects;

public class Message<T> {

    private final String sender;
    private final String recipient;
    private final T content;

    public Message(String sender, String recipient, T content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public T getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message<?> message = (Message<?>) o;
        return Objects.equals(sender, message.sender) &&
                Objects.equals(recipient, message.recipient) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, content);
    }
}