package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {

    private final String text;

    public MailMessage(String sender, String receiver, String text) {
        super(sender, receiver);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
