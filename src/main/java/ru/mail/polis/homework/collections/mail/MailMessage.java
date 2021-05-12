package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends BaseMail<String> {
    public MailMessage(String sender, String recipient, String item) {
        super(sender, recipient, item);
    }
}
