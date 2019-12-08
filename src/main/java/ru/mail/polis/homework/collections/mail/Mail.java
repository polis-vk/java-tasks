package ru.mail.polis.homework.collections.mail;

public class Mail <P> {
    public Mail(String sender,String addressee,P post){
        this.addressee=addressee;
        this.post=post;
        this.sender=sender;
    }

    public P getPost() {
        return post;
    }

    public String getAddressee() {
        return addressee;
    }

    public String getSender() {
        return sender;
    }

    private String sender;
    private String addressee;
    private P post;
}
