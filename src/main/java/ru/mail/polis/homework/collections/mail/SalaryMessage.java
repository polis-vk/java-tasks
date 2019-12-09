package ru.mail.polis.homework.collections.mail;

public class SalaryMessage extends Message{
    private int sum;

    public SalaryMessage(int sum, String rec, String sen) {
        super(rec, sen);
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }
}
