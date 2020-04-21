package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    public Salary(String sender, String recipient, int salary) {
        super(recipient, sender);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    private final int salary;
}
