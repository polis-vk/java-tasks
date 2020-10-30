package ru.mail.polis.homework.collections.streams.account;

import java.util.List;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {
  private final int id;
  private int balance;

  private List<Transaction> incomingTransactions;
  private List<Transaction> outgoingTransactions;

  public Account(int id, int balance, List<Transaction> incomingTransactions, List<Transaction> outgoingTransactions) {
    this.id = id;
    this.balance = balance;
    this.incomingTransactions = incomingTransactions;
    this.outgoingTransactions = outgoingTransactions;
  }

  public int getId() {
    return id;
  }

  public int getBalance() {
    return balance;
  }

  private void setBalance(int balance) {
    this.balance = balance;
  }
}
