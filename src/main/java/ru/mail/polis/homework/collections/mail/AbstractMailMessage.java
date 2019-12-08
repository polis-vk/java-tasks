package ru.mail.polis.homework.collections.mail;

public class AbstractMailMessage<T> {
    private String from;
    private String to;
    private T content;

    public AbstractMailMessage(String from, String to, T message){
        this.from = from;
        this.to = to;
        this.content = content;
    }
    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public T getMessage(){
        return content;
    }
}
