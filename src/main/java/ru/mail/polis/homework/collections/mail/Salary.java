package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Number> {
    public Salary(String sender, String recipient, Number sum) {
        super(sender, recipient, sum);
    }
}
