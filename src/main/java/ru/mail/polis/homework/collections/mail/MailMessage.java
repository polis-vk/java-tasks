package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Envelop<String> {

    public MailMessage(Client sender, Client recipient, String content) {
        super(sender, recipient, content);
    }
}
