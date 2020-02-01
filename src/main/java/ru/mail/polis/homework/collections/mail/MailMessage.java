package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {
    MailMessage(String sender, String recipient, String text) {
        super(sender, recipient, MailType.MAIL_TYPE_MESSAGE);
        this.text = text;
    }

    private final String text;

    public String getText() {
        return text;
    }
}
