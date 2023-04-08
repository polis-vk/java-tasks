package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Long> {
    public Salary(String receiver, String sender, Long salary) {
        super(receiver, sender, salary);
    }
}