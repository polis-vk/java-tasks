package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {

    public MailMessage(String message, String sender, String recipient) {
        super(message, sender, recipient);
    }
}
