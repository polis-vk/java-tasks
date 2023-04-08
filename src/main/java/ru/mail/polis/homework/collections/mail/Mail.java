package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<I> {
    private final String sender;
    private final String participant;
    private final I information;

    public Mail(String sender, String participant, I information) {
        this.sender = sender;
        this.participant = participant;
        this.information = information;
    }

    public String getSender() {
        return sender;
    }

    public String getParticipant() {
        return participant;
    }

    public I getInformation() {
        return information;
    }
}
