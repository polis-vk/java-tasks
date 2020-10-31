package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
  private Long id;
  private Long balance;
  private List<Transaction> inTransactions;
  private List<Transaction> outTransactions;

  public Account() {

  }

  public Account(Long id) {
    this.id = id;
    this.balance = 0L;
    this.inTransactions = new ArrayList<>();
    this.outTransactions = new ArrayList<>();
  }

  public Account(Long id, Long balance, List<Transaction> inTransactions, List<Transaction> outTransactions) {
    this.id = id;
    this.balance = balance;
    this.inTransactions = inTransactions;
    this.outTransactions = outTransactions;
  }

  public Long getId() {
    return id;
  }

  public Long getBalance() {
    return balance;
  }

  public void setBalance(Long balance) {
    this.balance = balance;
  }

  public List<Transaction> getInTransactions() {
    return inTransactions;
  }

  public List<Transaction> getOutTransactions() {
    return outTransactions;
  }
}
