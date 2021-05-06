package ru.mail.polis.homework.collections.mail;

public class CommonMessage<I> {
    private String to;
    private String from;
    private I information;

    public CommonMessage(String To, String From, I text) {
        this.to = To;
        this.from = From;
        this.information = text;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public I getInformation() {
        return information;
    }
}