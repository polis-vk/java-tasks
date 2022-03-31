package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Long> {
    public Salary(String sender, String recipient, Long sum) {
        super(sender, recipient, sum);
    }
}
