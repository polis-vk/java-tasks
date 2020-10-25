package ru.mail.polis.homework.collections.streams.account;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task {

    /**
     * Метод должен вернуть сумму всех исходящих транзакций с аккаунта
     * 2 балла
     */
    public static Map<String, Long> paymentsSumByAccount(List<Transaction> transactions) {
        Stream<Transaction> stream = transactions.stream();
        return stream.collect(Collectors
                .toMap(Transaction::getOutgoingId, Transaction::getSum));
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
    public static List<String> paymentsSumByAccount(List<Account> accounts, Date t, int n) {
        return accounts.stream()
                .sorted(Comparator.comparing(o -> o.getBalanceBeforeDate(t)))
                .skip(1)
                .limit(n)
                .map(Account::getId)
                .collect(Collectors.toList());
    }
}