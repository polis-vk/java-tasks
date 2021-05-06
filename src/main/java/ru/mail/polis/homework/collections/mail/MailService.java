package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {

    private final PopularMap<String, List<T>> recipients;
    private final PopularMap<String, Integer> senders;

    public MailService() {
        recipients = new PopularMap<>();
        senders = new PopularMap<>();
    }

    public MailService(PopularMap<String, List<T>> recipients, PopularMap<String, Integer> senders) {
        this.recipients = recipients;
        this.senders = senders;
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        recipients.computeIfAbsent(mail.getRecipient(), k -> new ArrayList<>()).add(mail);
        senders.compute(mail.getSender(), (key, value) -> value == null ? 1 : value + 1);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends Mail<?>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(service);
    }
}
