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
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService implements Consumer<Mail> {

    private final PopularMap<String, List<Mail>> recipientsMap = new PopularMap<>();
    private final PopularMap<String, List<Mail>> sendersMap = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Mail mail) {
        List<Mail> sender;
        sender = sendersMap.getOrDefault(mail.getSender(), null);
        if (sender == null) {
            sender = new ArrayList<>();
        }
        sender.add(mail);
        sendersMap.put(mail.getSender(), sender);
        List<Mail> recipient;
        recipient = sendersMap.getOrDefault(mail.getRecipient(), null);
        if (recipient == null) {
            recipient = new ArrayList<>();
        }
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
