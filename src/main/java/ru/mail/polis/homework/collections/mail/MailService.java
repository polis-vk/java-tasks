package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<T extends Sending> implements Consumer {

    private List<T> mails;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Object o) {
        T current = (T) o;
        mails.add(current);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<T>> getMailBox() {
        Map<String, List<T>> result = new HashMap<>();
        mails.forEach(mail -> result.merge(mail.getRecipient(), Collections.singletonList(mail), (oldList, newValue) -> {
            oldList.addAll(newValue);
            return oldList;
        }));
        return result;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        Map<String, Integer> senderAndFrequency = new HashMap<>();
        mails.forEach(mail -> senderAndFrequency.merge(mail.getSender(), 1, Integer::sum));
        return senderAndFrequency.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        Map<String, Integer> recipientAndFrequency = new HashMap<>();
        mails.forEach(mail -> recipientAndFrequency.merge(mail.getRecipient(), 1, Integer::sum));
        return recipientAndFrequency.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T> void process(MailService<? extends Sending> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }
}
