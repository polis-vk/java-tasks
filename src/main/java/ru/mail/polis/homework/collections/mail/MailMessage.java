package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail<String> {

    public MailMessage(String mailRecipient, String mailSender, String text) {
        super(mailRecipient, mailSender, text);
    }
}
