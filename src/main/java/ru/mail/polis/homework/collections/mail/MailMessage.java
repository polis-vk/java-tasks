package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Message<String> {
    public MailMessage(String recipient, String sender, String text) {
        super(recipient, sender, text);
    }
}
