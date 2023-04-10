package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    Salary(int salary, String senderName, String receiverName) {
        super(senderName, receiverName, salary);
    }
}