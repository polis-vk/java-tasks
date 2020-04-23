package ru.mail.polis.homework.collections.mail;

import java.math.BigDecimal;

public class Salary extends Mail<BigDecimal> {

    public Salary(String mailRecipient, String mailSender, BigDecimal sum) {
        super(mailRecipient, mailSender, sum);
    }
}
