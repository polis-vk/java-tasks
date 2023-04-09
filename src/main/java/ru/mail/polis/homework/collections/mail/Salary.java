package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    Salary(String sender, String recipient, Integer salary) {
        super(sender, recipient, salary);
    }
}
