package ru.mail.polis.homework.collections.mail;

public class MailMessage<String> extends AbstractMailMessage<String> {

    public MailMessage(java.lang.String sender, java.lang.String destination, String content) {
        super(sender, destination, content);
    }
}
