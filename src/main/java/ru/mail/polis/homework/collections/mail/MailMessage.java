package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Subject<String>{
    public MailMessage(String sender, String receiver, String body) {
        super(sender, receiver, body);
    }
}
