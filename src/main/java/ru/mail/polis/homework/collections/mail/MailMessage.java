package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail {

    private String message;
    public MailMessage(String receiver, String sender, String message){
        super(receiver, sender);
        this.message = message;
    }
}
