package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends MailItem<String> {
    public MailMessage(String sender, String receiver, String entry) {
        super(sender, receiver, entry);
    }
}
