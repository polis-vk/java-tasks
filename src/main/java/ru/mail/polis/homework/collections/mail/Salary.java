package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {

    private int amount;

    public Salary(String sender, String receiver, int amount) {
        super(sender, receiver);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}