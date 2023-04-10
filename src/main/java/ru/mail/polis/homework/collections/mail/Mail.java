package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail {

    private final String receiver;
    private final String sender;

    public Mail(String receiver, String sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "\nReceiver: " + receiver + "\nSender: " + sender;
    }
}
