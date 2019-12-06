package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    private int amount;

    public Salary(String sender, String receiver, Integer amount) {
        super(sender, receiver);
        this.amount = amount;
    }


}
