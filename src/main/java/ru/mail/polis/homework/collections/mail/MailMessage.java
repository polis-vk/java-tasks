package ru.mail.polis.homework.collections.mail;

public class MailMessage extends AbstractMailMessage<String>{
    public MailMessage(String from, String to, String text){
        super(from, to, text);
    }
}
