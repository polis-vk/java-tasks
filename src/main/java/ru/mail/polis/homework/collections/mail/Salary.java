package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {

    private int number;

    public Salary(String sender, String receiver, int number) {
        super(sender, receiver);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
