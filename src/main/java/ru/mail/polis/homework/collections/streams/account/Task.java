package ru.mail.polis.homework.collections.streams.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public class Task {

    /**
     * Метод должен вернуть сумму всех исходящих транзакций с аккаунта
     * 2 балла
     */
    public static Map<String, Long> paymentsSumByAccount(List<Transaction> transactions) {
        Map<String, Long> map = new HashMap<String, Long>();
        transactions.forEach(transaction -> {
            Long val = map.getOrDefault(transaction.getSender().getId(), 0L);
            map.put(transaction.getSender().getId(), val + transaction.getSum());
        });

        return map;
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

    public static long getAccountBalanceAtTime(Account account, long time){
        LongSummaryStatistics longS = account.getTransactionsList().stream()
                .filter(transaction -> transaction.getTimestamp() >= time)
                .collect(Collectors.summarizingLong(transaction ->{
                    long amount = transaction.getSum();
                    return transaction.getSender().getId().equals(account.getId()) ? amount : -amount;
                }));
        return account.getBalance() + longS.getSum();
    }

    public static List<String> paymentsSumByAccount(List<Account> accounts, long t, int n) {
        return
            accounts.stream()
                    .sorted( (a1, a2) -> {
                long a1Balance = getAccountBalanceAtTime(a1, t);
                long a2Balance = getAccountBalanceAtTime(a2, t);
                if(a1Balance > a2Balance) return -1;
                else if(a1Balance < a2Balance) return 1;
                return 0;
            })
                    .map(Account::getId)
                    .skip(1)
                    .limit(n)
                    .collect(Collectors.toList());

    }
}
