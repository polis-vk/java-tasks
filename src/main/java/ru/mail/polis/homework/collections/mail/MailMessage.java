package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String receiver, String sender, String text) {
        super(receiver, sender, text);
    }
}
