package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String recipient, String sender, String message) {
        super(recipient, sender, message);
    }
}
