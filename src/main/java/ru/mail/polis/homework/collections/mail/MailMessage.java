package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {

    public MailMessage(String recipient, String sender, String text) {
        super(recipient, sender, text);
    }

}
