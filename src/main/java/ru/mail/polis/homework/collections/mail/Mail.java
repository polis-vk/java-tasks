package ru.mail.polis.homework.collections.mail;

public interface Mail {

    String sender = null;
    String receiver = null;

    default String getSender() {
        return sender;
    }

    default String getReceiver() {
        return receiver;
    }

}
