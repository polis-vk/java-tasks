package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage<T> extends Incoming<T> {

    public MailMessage(String receiver, String sender, T content) {
        super(receiver, sender, content);
    }
}
