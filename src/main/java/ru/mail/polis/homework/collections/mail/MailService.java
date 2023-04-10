package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<M extends Mail<?>> implements Consumer<M> {
    private final Map<String, List<M>> senderList = new HashMap<>();
    private final PopularMap<String, String> mailBox = new PopularMap<>();


    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */

    public void accept(M mail) {
        senderList.computeIfAbsent(mail.getSender(), recipient -> new ArrayList<>()).add(mail);
        mailBox.put(mail.getSender(), mail.getSender());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<M>> getMailBox() {
        return senderList;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return mailBox.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return mailBox.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <M extends Mail<?>> void process(MailService<M> service, List<M> mails) {
        mails.forEach(service);
    }
}
