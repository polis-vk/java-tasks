package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends Letter<Long> {
    public Salary(String recipient, String sender, Long amount) {
        super(recipient, sender, amount);
    }
}
