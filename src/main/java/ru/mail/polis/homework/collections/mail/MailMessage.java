package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage<T> extends Mail<T> {

    public MailMessage(String recipient, String sender, T massageContent) {
        super(recipient, sender, massageContent);
    }

}
