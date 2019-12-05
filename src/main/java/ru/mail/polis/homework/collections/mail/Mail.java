package ru.mail.polis.homework.collections.mail;

public class Mail {

    private String sender;
    private String recipient;

    Mail(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    String getSender() {
        return sender;
    }

    String getRecipient() {
        return recipient;
    }
}
