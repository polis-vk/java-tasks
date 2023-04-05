package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage<I> {
    private final Mail sender;
    private final Mail recipient;
    private final I information;


    public MailMessage(Mail sender, Mail recipient, I information) {
        this.sender = sender;
        this.recipient = recipient;
        this.information = information;
    }

    public Mail getSender() {
        return sender;
    }

    public Mail getRecipient() {
        return recipient;
    }

    public I getInformation() {
        return information;
    }
}
