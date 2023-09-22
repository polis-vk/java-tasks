package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    Salary(String g, String s, Integer cost)
    {
        super(g,s, cost);
    }
    public Integer getCost()
    {
        return getAdditionalInformation();
    }
}
