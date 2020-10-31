package ru.mail.polis.homework.collections.streams.account;

import java.util.*;
import java.util.stream.Collectors;

public class Task {

    /**
     * Метод должен вернуть сумму всех исходящих транзакций с аккаунта
     * 2 балла
     */
    public static Map<String, Long> paymentsSumByAccount(List<Transaction> transactions) {

        return transactions.stream()
                .collect(Collectors.toMap(
                        Transaction::getOutgoingAccountIdAsStr,
                        Transaction::getSum,
                        Long::sum));
    }

    /**
     * Метод должен вернуть n айдишек аккаунтов, упорядоченных по величине баланса (от большего к меньшему)
     * на момент времени t, без учета самого "богатого" аккаунта (аккаунта с самым большим балансом
     * на момент времени t).
     * Если n >= accounts.size() - 1, то надо вернуть список длинной accounts.size() - 1
     * Пример:
     * account 1 на балансе имеет 500р и такой список транзакций
     *     (1 -> 2: 300р t1)
     *     (1 -> 2: 300р t2)
     *     (2 -> 1: 200р t3)
     * account 2 на балансе имеет 1000р и такой список транзакций
     *     (1 -> 2: 300р t1)
     *     (1 -> 2: 300р t2)
     *     (2 -> 1: 200р t3)
     * Тогда на момент времени t0 < t1 < t2 < t3 paymentsSumByAccount(accounts, t0, 1) = account 2
     * так как на счету account 2 на момент t0 было 1000 - 300 - 300 + 200 = 600, а на счету account 1
     * на момент t0 было 500 + 300 + 300 - 200 = 900
     *
     * Можно создавать любые доп классы и функции. Постарайтесь использовать как можно больше стримов
     * (обойтись без циклов и условий)
     * 3 балла
     */
    public static List<String> paymentsSumByAccount(List<Account> accounts, long t, int n) {

        return accounts.stream()
                .collect(Collectors.toMap(Account::getStrId, a -> a.getBalanceBefore(t)))
                .entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .skip(1)
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        Account acc1 = new Account();
        Account acc2 = new Account();
        Account acc3 = new Account();
        Account acc4 = new Account();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(new Date(), acc1, acc2, 100L));
        transactions.add(new Transaction(new Date(), acc1, acc3, 200L));
        transactions.add(new Transaction(new Date(), acc1, acc4, 300L));
        transactions.add(new Transaction(new Date(), acc2, acc4, 600L));
        transactions.add(new Transaction(new Date(), acc3, acc4, 500L));
        transactions.add(new Transaction(new Date(), acc3, acc4, 1000L));

        System.out.println(paymentsSumByAccount(transactions));

        try {
            long time = new Date().getTime();
            Thread.sleep(500);
            Date t1 = new Date();
            Thread.sleep(500);
            Date t2 = new Date();
            Thread.sleep(500);
            Date t3 = new Date();

            acc1.newTransaction(t1, acc1, acc2, 300L);
            acc1.newTransaction(t2, acc1, acc2, 300L);
            acc1.newTransaction(t3, acc2, acc1, 200L);
            acc1.setBalance(500L);

            acc2.newTransaction(t1, acc1, acc2, 300L);
            acc2.newTransaction(t2, acc1, acc2, 300L);
            acc2.newTransaction(t3, acc2, acc1, 200L);
            acc2.setBalance(1000L);

            List<Account> accounts = Arrays.asList(acc1, acc2);

            System.out.println(acc1.getBalanceBefore(time));
            System.out.println(acc2.getBalanceBefore(time));

            System.out.println(paymentsSumByAccount(accounts, time, 1));

        } catch (InterruptedException e) {
            System.out.println("please don't be mad, once i'll start writing normal tests");
        }
    }
}
