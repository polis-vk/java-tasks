package ru.mail.polis.homework.collections.mail;

public class Salary implements Mail {
    private String recipient;
    private String sender;
    private long salary;

    public Salary(String recipient, String sender, long salary) {
        this.recipient = recipient;
        this.sender = sender;
        this.salary = salary;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public String getSender() {
        return sender;
    }
}
