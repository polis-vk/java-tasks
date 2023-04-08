package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {

    public MailMessage(String sender, String participant, String information) {
        super(sender, participant, information);
    }
}
