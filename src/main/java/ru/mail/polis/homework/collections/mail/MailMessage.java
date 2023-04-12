package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String senderName, String receiverName, String textMassage) {
        super(senderName, receiverName, textMassage);
    }
}