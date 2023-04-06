package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String addressee, String sender, String text) {
        super(addressee, sender, text);
    }
}
