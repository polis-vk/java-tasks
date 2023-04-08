package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail {
    private final Integer salary;

    public Salary(String recipient, String sender, Integer salary) {
        super(recipient, sender);
        this.salary = salary;
    }

    public Integer getSalary() {
        return salary;
    }
}
