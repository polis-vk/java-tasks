package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {

    private String text;

    public MailMessage(String receiver, String sender, String text) {
        super(receiver, sender);
        this.text = text;
    }

    public String getText() {
        return text;
    }
