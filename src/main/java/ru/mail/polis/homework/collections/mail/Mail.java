package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<V> {
    private String senderName;
    private String receiverName;
    private V data;

    Mail(String senderName, String receiverName, V data) {
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.data = data;
    }

    String getSenderName() {
        return senderName;
    }

    String getReceiverName() {
        return receiverName;
    }
}
