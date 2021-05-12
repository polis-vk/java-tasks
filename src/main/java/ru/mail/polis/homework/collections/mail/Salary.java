package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends MessageContent<Integer> {
    public Salary(String sender, String recipient, Integer sumSalary) {
        super(sender, recipient, sumSalary);
    }
}
