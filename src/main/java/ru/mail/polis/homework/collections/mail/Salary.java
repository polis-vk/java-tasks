package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    private final int sum;

    public Salary(String sender, String receiver, int sum) {
        super(sender, receiver);
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }
}