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
    private final Long id;
    private LocalDate date;
    private final Account sourceAccount;
    private final Account targetAccount;
    private final Long sum;

    public Transaction(
            Long id,
            LocalDate date,
            Account sourceAccount,
            Account targetAccount,
            Long sum
    ) {
        this.id = id;
        this.date = date;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public Long getSum() {
        return sum;
    }
}
