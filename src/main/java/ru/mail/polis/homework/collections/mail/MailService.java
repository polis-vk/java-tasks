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
 * Всего 7 тугриков за пакет mail
 */
public class MailService implements Consumer<Mail> {

    List<Mail> sender = new ArrayList<>();
    List<Mail> recipient;
    private final PopularMap<String, List<Mail>> recipientsMap = new PopularMap<>();
    private final PopularMap<String, List<Mail>> sendersMap = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Mail mail) {
        sender = sendersMap.getOrDefault(mail.getSender(), sender);
        sender.add(mail);
        sendersMap.put(mail.getSender(), sender);
        recipient = sendersMap.getOrDefault(mail.getRecipient(), sender);
        recipient.add(mail);
        recipientsMap.put(mail.getRecipient(), recipient);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<Mail>> getMailBox() {
        return recipientsMap;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return sendersMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return recipientsMap.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }
}
