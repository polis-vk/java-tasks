package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail<String> {

    public MailMessage(String sender, String recipient, String message) {
        super(sender, recipient, message);
    }
}
