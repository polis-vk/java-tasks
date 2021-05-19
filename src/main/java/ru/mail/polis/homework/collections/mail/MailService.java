package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends Notification> implements Consumer<T> {
    private final PopularMap<String, List<Notification>> mailsToRecipients;
    private final PopularMap<String, List<Notification>> mailsFromSenders;

    public MailService() {
        this.mailsToRecipients = new PopularMap<>();
        this.mailsFromSenders = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T object) {
        mailsToRecipients.computeIfAbsent(object.getRecipient(), (l) -> new ArrayList<Notification>()).add(object);
        mailsFromSenders.computeIfAbsent(object.getSender(), (l) -> new ArrayList<Notification>()).add(object);
    }


    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<Notification>> getMailBox() {
        return mailsToRecipients;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return mailsFromSenders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return mailsToRecipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService service, List<Notification> mails) {
        for (Notification mail : mails) {
            service.accept(mail);
        }
    }
}
