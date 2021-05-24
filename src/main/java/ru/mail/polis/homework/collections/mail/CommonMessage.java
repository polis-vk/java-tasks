package ru.mail.polis.homework.collections.mail;

public class CommonMessage<I> {
    private final String to;
    private final String from;
    private final I information;

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