package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends InboxMessage<String> {

    public MailMessage(String sender, String addressee, String message) {
        super(sender, addressee, message);
    }

}
