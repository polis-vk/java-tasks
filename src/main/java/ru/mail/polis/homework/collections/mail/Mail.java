package ru.mail.polis.homework.collections.mail;

public interface Mail {
    String sender = null;
    String recipient = null;

    default String getSender() {
        return sender;
    }

    default String getRecipient() {
        return recipient;
    }

}
