package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */


public class MailService<T extends Mail<?>> implements Consumer<T> {
    // recipient /
    private Map<String, List<T>> recipientMailService = new HashMap<>();
    // recipient / sender
    private PopularMap<String, String> senderToRecipient = new PopularMap<>();


    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    public void accept(T mail) {
        String recipient = mail.getRecipient();
        String sender = mail.getSender();
        senderToRecipient.put(sender, recipient);
        recipientMailService.computeIfAbsent(sender, rec->new LinkedList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<T>> getMailBox() {
        return recipientMailService;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return senderToRecipient.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return senderToRecipient.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T extends Mail<?>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(service);
    }
}
