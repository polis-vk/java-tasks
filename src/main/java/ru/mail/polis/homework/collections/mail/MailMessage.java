package ru.mail.polis.homework.collections.mail;

public class MailMessage extends MailSomething<String> {

    public MailMessage(String mailReceiver, String mailSender, String message) {
        super(mailReceiver, mailSender, message);
    }
}
