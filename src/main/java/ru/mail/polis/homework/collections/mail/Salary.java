package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    public Salary(String receiver, String sender, Integer amount) {
        super(receiver, sender, amount);
    }
}
