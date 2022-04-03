package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    MailMessage(String sender, String recipient, String data) {
        super(sender, recipient, data);
    }
}
