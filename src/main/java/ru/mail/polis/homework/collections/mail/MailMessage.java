package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String sender, String recipient, String text) {
        super(sender, recipient, text);
    }
}

