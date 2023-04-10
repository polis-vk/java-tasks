package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail {

    private final String receiver;
    private final String sender;

    public Mail(String receiver, String sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getReceiver(){
        return this.receiver;
    }

    public String getSender(){
        return this.sender;
    }
}
