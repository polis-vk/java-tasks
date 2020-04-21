package ru.mail.polis.homework.collections.mail;

public class  Mail <C> {
    private C messageContent;
    private String sender;
    private String receiver;

    public  Mail(String sender, String receiver, C content){
        this.messageContent = content;
        this.sender=sender;
        this.receiver=receiver;
    }
    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public C getMessageContent() {
        return messageContent;
    }
}
