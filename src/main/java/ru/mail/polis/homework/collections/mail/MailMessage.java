package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail<String> {

    public MailMessage(String content, String sender, String recipient) {
        super(content, sender, recipient);
    }
}
