package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends MessagingUnit<String> {

    MailMessage(String receiver, String sender, String message) {
        super(receiver, sender, message);
    }
}
