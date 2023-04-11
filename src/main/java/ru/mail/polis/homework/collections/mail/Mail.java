package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail extends Sending {
    private final MailMessage mailMessage;

    public Mail(String sender, String recipient, MailMessage mailMessage) {
        super(sender, recipient);
        this.mailMessage = mailMessage;
    }

    public MailMessage getMailMessage() {
        return mailMessage;
    }
}
