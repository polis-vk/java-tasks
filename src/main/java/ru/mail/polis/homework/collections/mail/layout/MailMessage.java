package ru.mail.polis.homework.collections.mail.layout;

public class MailMessage extends MailLayout<String> {
    public MailMessage(String sender, String recipient, String text) {
        super(sender, recipient, text);
    }
}
