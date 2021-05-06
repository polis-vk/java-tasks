package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends Mail<Double> {
    public Salary(String sender, String receiver, Double salary) {
        super(sender, receiver, salary);
    }
}
