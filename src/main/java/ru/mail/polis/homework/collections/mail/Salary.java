package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail {
    private final int salaryAmount;

    public Salary(String sender, String recipient, int salary) {
        super(sender, recipient);
        this.salaryAmount = salary;
    }

    public int getSalaryAmount() {
        return salaryAmount;
    }
}
