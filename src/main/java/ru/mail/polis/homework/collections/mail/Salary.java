package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    public Salary(String recipient, String sender, Integer sum) {
        super(recipient, sender, sum);
    }
}
