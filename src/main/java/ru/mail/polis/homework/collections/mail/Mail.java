package ru.mail.polis.homework.collections.mail;

public class Mail <T>{
    private String sender;
    private String receiver;
    private T content;

    Mail (String sender, String receiver, T content){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
    
}
