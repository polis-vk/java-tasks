package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Message<String> {
    public MailMessage(String sender, String recipient, String content) {
        super(sender, recipient, content);
    }
}
