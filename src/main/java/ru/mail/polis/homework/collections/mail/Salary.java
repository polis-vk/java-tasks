package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Double> {
    Salary(String sender, String recipient, Double amount){
        super(sender, recipient, amount);
    }
}
