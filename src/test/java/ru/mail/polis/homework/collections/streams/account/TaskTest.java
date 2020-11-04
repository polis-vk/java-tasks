package ru.mail.polis.homework.collections.streams.account;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static ru.mail.polis.homework.collections.streams.account.Task.paymentsSumByAccount;

public class TaskTest extends TestCase {

    public void testPaymentsSumByAccount() {
        Account a1 = new Account("a1", 900L, new ArrayList<>());
        Account a2 = new Account("a2", 600L, new ArrayList<>());
        a1.makeTransaction(a2, 300L);
        a1.makeTransaction(a2, 300L);
        a2.makeTransaction(a1, 200L);
        Map<String, Long> map = paymentsSumByAccount(a1.getTransactionsList());
        Long a1Sum = map.get("a1");

        assertEquals(600L, a1Sum.longValue());
    }

    public void testGetAccountBalanceAtTime() {
        Account a1 = new Account("a1", 900L, new ArrayList<>());
        Account a2 = new Account("a2", 600L, new ArrayList<>());
        long t0 = Calendar.getInstance().getTimeInMillis();
        a1.makeTransaction(a2, 300L);
        a1.makeTransaction(a2, 300L);
        a2.makeTransaction(a1, 200L);

        assertEquals(500L, a1.getBalance());
        assertEquals(1000L, a2.getBalance());

        assertEquals(900L,Task.getAccountBalanceAtTime(a1, t0));
        assertEquals(600L,Task.getAccountBalanceAtTime(a2, t0));
    }

    public void testTestPaymentsSumByAccount() {
        Account a1 = new Account("a1", 900L, new ArrayList<>());
        Account a2 = new Account("a2", 600L, new ArrayList<>());
        long t0 = Calendar.getInstance().getTimeInMillis();
        a1.makeTransaction(a2, 300L);
        a1.makeTransaction(a2, 300L);
        a2.makeTransaction(a1, 200L);
        List<Account> accs = new ArrayList<>();
        accs.add(a1);
        accs.add(a2);

        List<String> str = paymentsSumByAccount(accs, t0, 2 );
        assertEquals(1, str.size());
        assertEquals(a2.getId(), str.get(0));
    }
}