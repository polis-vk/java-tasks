package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class MailMessage extends CommonMessage<String> {

    public MailMessage(String To, String From, String Message) {
        super(To, From, Message);
    }
}
