package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String sender, String addressee, String message) {
        super(sender, addressee, message);
    }
}
