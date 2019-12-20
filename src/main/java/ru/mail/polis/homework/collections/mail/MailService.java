package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
class MailService<T extends Mail<?>> implements Consumer<T> {

    private PopularMap<String, List<T>> recipients;
    private PopularMap<String, List<T>> senders;

    MailService() {
        recipients = new PopularMap<>();
        senders = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        senders.computeIfAbsent(mail.getSender(), key -> new ArrayList<>()).add(mail);
        recipients.computeIfAbsent(mail.getRecipient(), key -> new ArrayList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    Map<String, List<T>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    String getPopularRecipient() {
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    void process(MailService<T> mailService, List<T> mail) {
        mail.forEach(mailService);
    }
}
