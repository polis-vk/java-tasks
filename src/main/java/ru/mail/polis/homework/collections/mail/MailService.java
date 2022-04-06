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
public class MailService implements Consumer<Mail<? extends Object>> {

    private final Map<String, List<Mail>> incomingMail;
    private final PopularMap<String, String> recipientAndSenderPopularity;

    MailService() {
        incomingMail = new HashMap<>();
        recipientAndSenderPopularity = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */

    public void accept(Mail mail) {
        incomingMail.computeIfAbsent(mail.getReceiver(), reciever -> new ArrayList<>()).add(mail);
        recipientAndSenderPopularity.put(mail.getReceiver(), mail.getSender());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<Mail>> getMailBox() {
        return incomingMail;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return recipientAndSenderPopularity.getPopularValue();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return recipientAndSenderPopularity.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List<Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }
}
