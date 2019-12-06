package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {

    String text;

    public MailMessage(String sender, String receiver, String text) {
        super(sender, receiver);
        this.text = text;
    }

}
