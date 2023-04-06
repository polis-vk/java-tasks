package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Long> {
    public Salary(String addressee, String sender, Long salary) {
        super(addressee, sender, salary);
    }
}