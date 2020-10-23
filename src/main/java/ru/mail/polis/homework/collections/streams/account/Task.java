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
                .collect(Collectors.toMap(Transaction::getOutgoingAccountIDAndBalance, Transaction::getSum, Long::sum));
    }

    /**
     * Метод должен вернуть n айдишек аккаунтов, упорядоченных по величине баланса (от большего к меньшему)
     * на момент времени t, без учета самого "богатого" аккаунта (аккаунта с самым большим балансом
     * на момент времени t).
     * Если n >= accounts.size() - 1, то надо вернуть список длинной accounts.size() - 1
     * Пример:
     * account 1 на балансе имеет 500р и такой список транзакций
     * (1 -> 2: 300р t1)
     * (1 -> 2: 300р t2)
     * (2 -> 1: 200р t3)
     * account 2 на балансе имеет 1000р и такой список транзакций
     * (1 -> 2: 300р t1)
     * (1 -> 2: 300р t2)
     * (2 -> 1: 200р t3)
     * Тогда на момент времени t0 < t1 < t2 < t3 paymentsSumByAccount(accounts, t0, 1) = account 2
     * так как на счету account 2 на момент t0 было 1000 - 300 - 300 + 200 = 600, а на счету account 1
     * на момент t0 было 500 + 300 + 300 - 200 = 900
     * <p>
     * Можно создавать любые доп классы и функции. Постарайтесь использовать как можно больше стримов
     * (обойтись без циклов и условий)
     * 3 балла
     */
    public static List<String> paymentsSumByAccount(List<Account> accounts, long t, int n) {
        return accounts.stream()
                .limit(n)
                .sorted(Comparator.comparing(account -> -account.getBalanceByDate(t)))
                .map(Account::toString)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Account account1 = new Account();
        Account account2 = new Account();
        Date date1 = new Date(1);
        Date date2 = new Date(1000);
        Date date3 = new Date(10000);
        Date date4 = new Date(200000);
        Date date5 = new Date(3000000);

        Transaction transaction1 = new Transaction(date1, account1, account2, 100);
        Transaction transaction2 = new Transaction(date2, account1, account2, 200);
        Transaction transaction3 = new Transaction(date3, account1, account2, 500);
        Transaction transaction4 = new Transaction(date4, account2, account1, 1000);
        Transaction transaction5 = new Transaction(date5, account2, account1, 200);

        List<Transaction> outTransactions1 = new LinkedList<>();
        outTransactions1.add(transaction1);
        outTransactions1.add(transaction2);
        outTransactions1.add(transaction3);

        List<Transaction> inTransactions1 = new LinkedList<>();
        inTransactions1.add(transaction4);

        account1.setOutgoingTransactions(outTransactions1);
        account1.setIncomingTransactions(inTransactions1);

        List<Transaction> outTransactions2 = new LinkedList<>();
        outTransactions2.add(transaction4);
        outTransactions2.add(transaction5);

        List<Transaction> inTransactions2 = new LinkedList<>();
        inTransactions2.add(transaction1);
        inTransactions2.add(transaction2);
        inTransactions2.add(transaction3);

        account2.setIncomingTransactions(inTransactions2);
        account2.setOutgoingTransactions(outTransactions2);

        System.out.println(paymentsSumByAccount(outTransactions1));

        List<Account> accounts = new LinkedList<>();
        accounts.add(account1);
        accounts.add(account2);

        System.out.println(paymentsSumByAccount(accounts, 10000000, 2));
    }
}
