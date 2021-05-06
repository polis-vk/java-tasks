package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Letter<String> {
    public MailMessage(String recipient, String sender, String message) {
        super(recipient, sender, message);
    }
}
