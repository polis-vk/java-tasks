package ru.mail.polis.homework.collections.streams.account;

import java.util.*;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws InterruptedException {
        Account account1= new Account(1000);
        Account account2= new Account(2000);
        Account account3= new Account(3000);
        Date t = new Date();
        account1.translateTo(account2, 100);
        account1.translateTo(account2, 100);
        account1.translateTo(account2, 100);
        account1.translateTo(account2, 100);
        account2.translateTo(account3, 2000);
        System.out.println(paymentsSumByAccount(List.of(account2, account3, account1), t.getTime(), 5));
    }

    /**
     * Метод должен вернуть сумму всех исходящих транзакций с аккаунта
     * 2 балла
     */
    public static Map<String, Long> paymentsSumByAccount(List<Transaction> transactions) {
        return transactions.stream()
                .collect(HashMap::new,
                        (map, transaction) ->
                                map.merge(transaction.getOutAccount().getId(), transaction.getSum(), Long::sum),
                        HashMap::putAll);
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
                .sorted(Comparator.comparingLong(account ->
                                account.getTransactionList().stream()
                                        .filter(transaction -> transaction.getDate().getTime() >= t)
                                        .reduce( - account.getBalance(),
                                                (aLong, transaction) ->
                                                        aLong + (transaction.getOutAccount() == account ?
                                                                - transaction.getSum() :
                                                                transaction.getSum()),
                                                Long::sum)
                        )
                )
                .map(Account::getId)
                .skip(1)
                .limit(n)
                .collect(Collectors.toList());
    }
}
