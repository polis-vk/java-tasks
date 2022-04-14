package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    public Salary(String sender, String addressee, Integer salarySize) {
        super(sender, addressee, salarySize);
    }
}
