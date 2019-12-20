package ru.mail.polis.homework.collections.mail;

class Mail<T> {

    private String sender;
    private String recipient;
    private T data;

    Mail(String sender, String recipient, T data){
        this.sender = sender;
        this.recipient = recipient;
        this.data = data;
    }

    String getSender(){
        return sender;
    }

    String getRecipient(){
        return recipient;
    }

    T getData(){
        return data;
    }

}
