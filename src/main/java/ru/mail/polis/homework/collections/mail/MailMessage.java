package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Correspondency<String> {

    public MailMessage(String sender, String receiver, String text) {
        super(sender, receiver, text);
    }
}
