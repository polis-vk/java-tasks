package ru.mail.polis.homework.collections.mail;

abstract public class Mail {
    private String sender;
    private String receiver;

    /* package-private */
    Mail(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    /* package-private */
    String getSender() {
        return sender;
    }

    /* package-private */
    String getReceiver() {
        return receiver;
    }
}