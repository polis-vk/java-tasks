package ru.mail.polis.homework.collections.mail;

import org.w3c.dom.Text;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    public MailMessage(String sender, String addressee, String textMessage) {
        super(sender, addressee, textMessage);
    }
}
