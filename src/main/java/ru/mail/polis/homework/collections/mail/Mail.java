package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T additional;
    Mail(String g, String s, T additional)
    {
        this.recipient = g;
        this.sender  = s;
        this.additional = additional;
    }

    public String getRecipient() {
        return recipient;
    }
    public String getSender()
    {
        return sender;
    }
    public T getAdditionalInformation() {return additional;}
}
