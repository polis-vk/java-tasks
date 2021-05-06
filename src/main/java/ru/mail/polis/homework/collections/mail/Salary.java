package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends CommonMessage<Double> {

    public Salary(String To, String From, Double Salary) {
        super(To, From, Salary);
    }
}
