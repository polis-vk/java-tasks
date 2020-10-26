package ru.mail.polis.homework.collections.streams;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.collections.streams.account.Account;
import ru.mail.polis.homework.collections.streams.account.Task;
import ru.mail.polis.homework.collections.streams.account.Transaction;

import java.util.Arrays;
import java.util.List;

public class TestTask {

    @Test
    public void paymentsSumByAccountTest(){
        Account account1 = new Account(10, 300L);
        Account account2 = new Account(30, 200L);
        Transaction in1 = new Transaction(1, 1, account1, account2, 100L);
        account1.addTransaction(in1);
        account2.addTransaction(in1);
        Transaction out1 = new Transaction(2, 2, account2, account1, 50L);
        account1.addTransaction(out1);
        account2.addTransaction(out1);
        Transaction in2 = new Transaction(3, 3, account1, account2, 100L);
        account1.addTransaction(in2);
        account2.addTransaction(in2);

        long obtained = Task.paymentsSumByAccount(account1);

        Assert.assertEquals(200l, obtained);
    }

    @Test
    public void paymentsSumByAccountTest_GetAccountsLessThanN_ReturnNId(){
        Account account1 = new Account(1, 200L);
        Account account2 = new Account(2, 300L);
        Account account3 = new Account(3, 400L);
        Account account4 = new Account(4, 500L);
        Transaction one = new Transaction(1, 1, account4, account1, 400L);
        one.makeTransaction();
        account1.addTransaction(one);
        account4.addTransaction(one);
        Transaction two = new Transaction(2, 2, account2, account3, 300L);
        two.makeTransaction();
        account2.addTransaction(two);
        account3.addTransaction(two);
        List<Account> accounts = Arrays.asList(account1,account2,account3,account4);

        List<Integer> obtained = Task.paymentsSumByAccount(accounts, 1, 10);

        List<Integer> expected = Arrays.asList(3,2,4);
        Assert.assertArrayEquals(expected.toArray(), obtained.toArray());
    }

    @Test
    public void paymentsSumByAccountTest_GetAccountsMoreThanN_ReturnAccountsSizeMinusOneId(){
        Account account1 = new Account(1, 200L);
        Account account2 = new Account(2, 300L);
        Account account3 = new Account(3, 400L);
        Account account4 = new Account(4, 500L);
        Transaction one = new Transaction(1, 1, account4, account1, 400L);
        one.makeTransaction();
        account1.addTransaction(one);
        account4.addTransaction(one);
        Transaction two = new Transaction(2, 2, account2, account3, 300L);
        two.makeTransaction();
        account2.addTransaction(two);
        account3.addTransaction(two);
        List<Account> accounts = Arrays.asList(account1,account2,account3,account4);

        List<Integer> obtained = Task.paymentsSumByAccount(accounts, 1, 2);

        List<Integer> expected = Arrays.asList(3,2);
        Assert.assertArrayEquals(expected.toArray(), obtained.toArray());
    }

}
