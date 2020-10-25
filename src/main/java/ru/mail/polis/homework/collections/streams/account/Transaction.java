package ru.mail.polis.homework.collections.streams.account;

import java.time.LocalDate;

/**
 * Реализуйте класс Transaction с полями:
 * id
 * дата
 * исходящий аккаунт
 * аккаунт получателя
 * сумма
 * 1 балл
 */
public class Transaction {
    private Long id;
    private LocalDate date;
    private Account sourceAccount;
    private Account targetAccount;
    private Long sum;

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getSum() {
        return sum;
    }
}
