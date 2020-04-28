package ru.mail.polis.homework.collections.mail;

public class Message extends Mail<String> {

    public Message(String addressee, String sender, String message) {
        super(addressee, sender, message);
    }
}
