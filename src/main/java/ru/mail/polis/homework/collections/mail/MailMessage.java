package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Mail<String> {

    public MailMessage(String recipient, String sender, String content) {
        super(recipient, sender, content);
    }

}
