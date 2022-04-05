package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import ru.mail.polis.homework.collections.PopularMap;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<T extends Mail<T>> implements Consumer<Mail<T>> {

    private final PopularMap<String, List<Mail<T>>> recipients;
    private final PopularMap<String, List<Mail<T>>> senders;

    public MailService() {
        this.recipients = new PopularMap<>();
        this.senders = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Mail<T> mail) {
        senders.computeIfAbsent(mail.getSender(), mails -> new ArrayList<>()).add(mail);
        recipients.computeIfAbsent(mail.getRecipient(), mails -> new ArrayList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<Mail<T>>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T extends Mail<T>> void process(MailService<T> service, List<? extends Mail<T>> mails) {
        mails.forEach(service);
    }
}
