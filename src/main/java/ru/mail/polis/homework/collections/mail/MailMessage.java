package ru.mail.polis.homework.collections.mail;

public class MailMessage extends BasicTransfer<String> {
    
    public MailMessage(String data, String sender, String recipient) {
        super(data, sender, recipient);
    }
    
}
