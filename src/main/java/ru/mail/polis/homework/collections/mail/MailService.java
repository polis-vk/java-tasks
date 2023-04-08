package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.HashMap;
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
public class MailService<T extends Mail> implements Consumer<T> {
    private PopularMap<String, String> senderRecipientMap;
    private Map<String, List<T>> mailBox;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    public MailService() {
        this.senderRecipientMap = new PopularMap<>();
        this.mailBox = new HashMap<>();
    }

    @Override
    public void accept(T o) {
        if (o == null) {
            return;
        }
        senderRecipientMap.put(o.getSender(), o.getRecipient());
        mailBox.computeIfAbsent(o.getRecipient(), k -> new ArrayList<T>()).add(o);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<T>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return senderRecipientMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return senderRecipientMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T extends Mail> void process(MailService service, List<T> mails) {
        mails.forEach(v -> service.accept(v));
    }

}
