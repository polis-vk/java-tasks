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
public class MailService<T extends Mail> implements Consumer {

    private List<T> mails;
    private Map<String, List<T>> mailBox;
    private Map<String, Integer> senderAndFrequency;
    private Map<String, Integer> recipientAndFrequency;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Object o) {
        T currentMail = (T) o;
        mailBox.merge(currentMail.getRecipient(), Collections.singletonList(currentMail), (oldList, newValue) -> {
            oldList.addAll(newValue);
            return oldList;
        });
        senderAndFrequency.merge(currentMail.getSender(), 1, Integer::sum);
        recipientAndFrequency.merge(currentMail.getRecipient(), 1, Integer::sum);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<T>> getMailBox() {
        return mailBox;
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
        return recipientAndFrequency.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T> void process(MailService<? extends Mail> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }
}
