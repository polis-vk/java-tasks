package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends Mail<Integer> {
    public Salary(String sender, String recipient, Integer sum) {
        super(sender, recipient, sum);
    }
}
