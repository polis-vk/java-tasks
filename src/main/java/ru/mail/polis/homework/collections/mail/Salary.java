package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    public Salary(String sender, String recipient, int salary) {
        super(sender, recipient, salary);
    }
}
