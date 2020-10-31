package ru.mail.polis.homework.collections.streams.account;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
  private final LocalDateTime date;
  private final Account sourceAccount;
  private final Account targetAccount;
  private final long sum;

  public Transaction(Long id,
                     LocalDateTime date,
                     Account sourceAccount,
                     Account targetAccount,
                     long sum) {
    this.id = id;
    this.date = date;
    this.sourceAccount = sourceAccount;
    this.targetAccount = targetAccount;
    this.sum = sum;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Account getSourceAccount() {
    return sourceAccount;
  }

  public Account getTargetAccount() {
    return targetAccount;
  }

  public long getSum() {
    return sum;
  }
}
