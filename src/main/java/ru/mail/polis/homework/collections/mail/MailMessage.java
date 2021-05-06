package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String sender, String receiver, String message) {
        super(sender, receiver, message);
    }
}
