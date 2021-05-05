package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends Incoming<String> {

    public MailMessage(String receiver, String sender, String incomingObject) {
        super(receiver, sender, incomingObject);
    }
}
