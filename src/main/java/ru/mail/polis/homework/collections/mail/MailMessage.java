package ru.mail.polis.homework.collections.mail;

public class MailMessage<T> extends Message<T> {

    public MailMessage(String sender, String receiver, T massageContent) {
        super(sender, receiver, massageContent);
    }
}
