package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String senderName;
    private final String receiverName;
    private final T data;
    public Mail(String senderName, String receiverName, T data) {
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.data = data;
    }
    public String getSenderName() {
        return senderName;
    }
    public String getReceiverName() {
        return receiverName;
    }
}
