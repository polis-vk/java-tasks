package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    private Long sum;

    public Salary(String recipient, String sender, Long sum) {
        super(recipient, sender);
        this.sum = sum;
    }

    public Long getSum() {
        return sum;
    }
}
