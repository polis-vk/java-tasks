package ru.mail.polis.homework.collections.mail;

public class Salary implements Mail{

    private String sender;
    private String receiver;
    private long salary;

    public Salary(String sender, String receiver, long salary) {
        this.sender = sender;
        this.receiver = receiver;
        this.salary = salary;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public long getSalary() {
        return salary;
    }

}
