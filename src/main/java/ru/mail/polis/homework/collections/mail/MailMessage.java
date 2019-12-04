package ru.mail.polis.homework.collections.mail;

public class MailMessage extends MailWorker {
    private String text;

    public MailMessage(String talker, String listener, String text) {
        super (talker, listener);

        this.text = text;
    }
}
