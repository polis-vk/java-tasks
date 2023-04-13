package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {

    private final String receiver;
    private final String sender;
    private final T artifact;

    public Mail(String receiver, String sender, T artifact) {
        this.receiver = receiver;
        this.sender = sender;
        this.artifact = artifact;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public T getArtifact() {
        return artifact;
    }
}
