package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountList {
    private List<Account> accounts;
    private long idCounter;

    public AccountList() {
        accounts = new ArrayList<>();
        idCounter = 0;
    }

    public AccountList(long idCounter) {
        accounts = new ArrayList<>();
        this.idCounter = idCounter;
    }

    public void add(Account newAccount) throws NullPointerException, IllegalArgumentException {
        if (newAccount != null) {
            if (newAccount.getId() > idCounter) {
                accounts.add(newAccount);
                idCounter++;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new NullPointerException();
        }
    }

    public long getIdCounter() {
        return idCounter;
    }

    public Account getByIndex(int index) {
        return accounts.get(index);
    }

    public int size() {
        return accounts.size();
    }
}
