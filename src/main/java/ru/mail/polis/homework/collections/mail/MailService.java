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
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService implements Consumer<MailMessage> {
    private final PopularMap<String, List<MailMessage>> sendersList = new PopularMap<>();
    private final PopularMap<String, List<MailMessage>> recipientsList = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(MailMessage o) {
        List<MailMessage> senderMailList = new ArrayList<>();
        senderMailList = sendersList.getOrDefault(o.getSender(), senderMailList);
        senderMailList.add(o);
        sendersList.put(o.getSender(), senderMailList);

        List<MailMessage> recipientMailList = new ArrayList<>();
        recipientMailList = recipientsList.getOrDefault(o.getRecipient(), recipientMailList);
        recipientMailList.add(o);
        recipientsList.put(o.getSender(), recipientMailList);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     *
     * @return
     */
    public Map<String, List<MailMessage>> getMailBox() {
        return recipientsList;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return sendersList.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return recipientsList.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List<MailMessage> mails) {
        mails.forEach(service);
    }
}
