package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail{

    private String[] text;

    public MailMessage(String sender, String recipient, String[] text) {
        super(sender, recipient);
        this.text =text;
    }

    public String[] getText() {
        return text;
    }
}

