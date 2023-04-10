package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail {

    private final String message;

    public MailMessage(String receiver, String sender, String message) {
        super(receiver, sender);
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString() + "\nMessage: " + message + "\n";
    }
}
