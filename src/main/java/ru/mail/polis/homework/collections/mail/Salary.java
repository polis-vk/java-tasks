package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    Salary(String sender, String recipient, long sum) {
        super(sender, recipient, MailType.MAIL_TYPE_SALARY);
        this.sum = sum;
    }
    private final long sum;

    public long getSum() {
        return sum;
    }
}
