package ru.mail.polis.homework.collections.streams.account;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task {

    /**
     * Метод должен вернуть сумму всех исходящих транзакций с аккаунта
     * 2 балла
     * @return
     */
    public static Map<Long, Long> paymentsSumByAccount(List<Transaction> transactions) {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::getIdSender, Collectors.summingLong(Transaction::getSum)));
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
    public static List<Long> paymentsSumByAccount(List<Account> accounts, long t, int n) {
        // TODO: 29.10.2020
        return accounts.stream().sorted(Comparator.comparing((Account account) -> getBalanceByTime(account, t)).reversed()).map(Account::getId).skip(1).limit(n).collect(Collectors.toList());
    }
    public static long getBalanceByTime(Account account, long time) {
        return account.getSum() - account.getTransactionList().stream().filter(i -> i.getIdSender() == account.getId())
                .filter(i -> i.getDate() > time).mapToLong(Transaction::getSum).sum()
                + account.getTransactionList().stream().filter(i -> i.getIdRecipient() == account.getId())
                .filter(i -> i.getDate() > time).mapToLong(Transaction::getSum).sum()
                ;
    }
}
