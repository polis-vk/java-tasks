package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String recipient;
    private final String sender;
    private final T letterContent;

    public Mail(String receiver, String sender, T letterContent) {
        this.recipient = receiver;
        this.sender = sender;
        this.letterContent = letterContent;
    }

    public String getReceiver(){
        return recipient;
    }

    public String getSender(){
        return sender;
    }

    public T getLetterContent(){
        return letterContent;
    }
}
