package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Long> {

    public Salary(String recipient, String sender, Long salary) {
        super(recipient, sender, salary);
    }
}
