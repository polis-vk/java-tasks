package ru.mail.polis.homework.collections.streams.account;

import java.util.Date;

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
    private final int id;
    private final long data ;
    private final Account outAccount; //исходящий аккаунт
    private final Account inAccount; //получатель
    private final Long sum;
    private boolean maked = false;

    public Transaction(int id, long data, Account outAccount, Account inAccount, Long sum) {
        this.id = id;
        this.data = data;
        this.outAccount = outAccount;
        this.inAccount = inAccount;
        this.sum = sum;
    }

    public long getData() {
        return data;
    }

    //создают обратную транзакцию
    public Transaction getObrTransaction(){
        return new Transaction(-id, data,inAccount, outAccount, sum);
    }

    //выполняет транзакцию
    public void makeTransaction(){
        outAccount.minusSum(sum);
        inAccount.addSum(sum);
        maked = true;
    }

    public Account getOutAccount() {
        return outAccount;
    }

    public Account getInAccount(){ return inAccount;}

    public Long getSum() {
        return sum;
    }
}
