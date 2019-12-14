package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    private final int sum;

    public Salary(String sender, String recipient, int sum) {
        super(sender, recipient);
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }
}