package ru.mail.polis.homework.collections.mail;


/**
 * 1 тугрик
 */
public class MailMessage extends Mail {
    private final String textMessage;

    MailMessage(String recipient, String sender, String textMessage) {
        super(recipient, sender);
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return this.textMessage;
    }
}
