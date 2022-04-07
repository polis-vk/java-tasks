package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends Mail<Long> {
    public Salary(String sender, String recipient, Long message) {
        super(sender, recipient, message);
    }
}
