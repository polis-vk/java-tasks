package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    MailMessage(String sender, String receiver, String message) {
        super(sender, receiver, message);
    }
}
