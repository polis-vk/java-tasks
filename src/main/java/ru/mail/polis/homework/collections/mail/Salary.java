package ru.mail.polis.homework.collections.mail;

public class Salary {
    private final String recipient;
    private final String sender;
    private final int salary;

    public Salary(String recipient, String sender, int salary) {
        this.recipient = recipient;
        this.sender = sender;
        this.salary = salary;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }
}
