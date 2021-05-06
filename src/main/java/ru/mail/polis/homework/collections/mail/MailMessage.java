package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String sender, String address, String message) {
        super(sender, address, message);
    }
}
