package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    private final int sum;

    public Salary(String sender, String recipient, int sum) {
        super(sender, recipient);
        this.sum = sum;
    }

    @Override
    public Integer getContent() {
        return sum;
    }

    @Override
    public String toString() {
        return super.toString() + "Salary of " + sum + ".";
    }
}
