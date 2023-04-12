package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail {
    private final int salary;

    Salary(String recipient, String sender, int salary) {
        super(recipient, sender);
        this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }
}
