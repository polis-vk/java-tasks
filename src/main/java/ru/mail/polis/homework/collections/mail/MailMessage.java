package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends MessageContent<String> {
    public MailMessage(String sender, String recipient, String message) {
        super(sender, recipient, message);
    }
}
