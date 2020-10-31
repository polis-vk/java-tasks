package ru.mail.polis.homework.collections.streams.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализуйте класс Account с полями:
 * id
 * список всех транзакций с аккаунта (входящие и исходящие)
 * баланс
 * 1 балл
 */
@EqualsAndHashCode(exclude = "transactionList")
public class Account {

    private static int               count = 0; // to create id
    @Getter
    private final  String            id;
    @Getter
    private final  List<Transaction> transactionList;
    @Getter @Setter
    private        long              balance;

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
                .filter(transaction -> transaction.getOutAccount().equals(this))
                .collect(Collectors.toList());
    }

    public List<Transaction> getInTranslation() {
        return transactionList.stream()
                .filter(transaction -> transaction.getInAccount().equals(this))
                .collect(Collectors.toList());
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
