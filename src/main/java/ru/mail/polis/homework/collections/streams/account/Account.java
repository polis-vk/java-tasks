package ru.mail.polis.homework.collections.streams.account;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
public class Account {

    private static                int               count = 0; // to create id
    private final @Getter         String            id;
    private       @Getter @Setter List<Transaction> transactionList;
    private       @Getter @Setter long              balance;

    public Account(int balance) {
        this.id = String.valueOf(count++);
        this.transactionList = new ArrayList<>();
        this.balance = balance;
    }

    private void addTranslate(Transaction transaction){
        transactionList.add(transaction);
    }

    public void translateTo(Account recipient, long quantity) {
        Transaction transaction = new Transaction(this, recipient, quantity);
        addTranslate(transaction);
        recipient.addTranslate(transaction);
        this.balance -= quantity;
        recipient.balance += quantity;
    }

    public List<Transaction> getOutTranslation() {
        return transactionList.stream()
                .filter(transaction -> transaction.getOutAccount() == this)
                .collect(Collectors.toList());
    }

    public List<Transaction> getInTranslation() {
        return transactionList.stream()
                .filter(transaction -> transaction.getInAccount() == this)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return balance == account.balance &&
                Objects.equals(id, account.id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", transactionList=" + transactionList +
                ", balance=" + balance +
                '}';
    }
}
