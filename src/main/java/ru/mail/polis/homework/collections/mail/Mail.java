package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail extends MailMessage<String > {
    public Mail(String sender, String recipient, String message) {
        super(sender, recipient, message);
    }
}
