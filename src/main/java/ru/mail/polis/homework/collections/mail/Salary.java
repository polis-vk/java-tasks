package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Sending {
    private final double salaryAmount;

    public Salary(String sender, String recipient, double salary) {
        super(sender, recipient);
        this.salaryAmount = salary;
    }

    public double getSalaryAmount() {
        return salaryAmount;
    }
}
