package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail{
    private String text;

    public MailMessage(String recipient, String sender, String text) {
        super(recipient, sender);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
