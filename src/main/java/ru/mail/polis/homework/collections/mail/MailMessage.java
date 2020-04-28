package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail<String> { //T - String
    public MailMessage(String sender, String recipient, String msg) {
        super(msg, recipient, sender);
    }
}
