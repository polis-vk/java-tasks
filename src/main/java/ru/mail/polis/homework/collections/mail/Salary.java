package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail<Number> {
    public Salary(String sender, String receiver, Number salary) {
        super(sender, receiver, salary);
    }
}
