package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<T extends Mail<T>> implements Consumer<T> {
    private final Map<String, List<T>> recipientDict = new HashMap<String, List<T>>();
    private final Map<String, List<T>> sendertDict = new HashMap<>();


    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */


    @Override
    public void accept(T mailObject) {
        String recipient = mailObject.recipient;
        String sender = mailObject.sender;
        processing(recipient, recipientDict, mailObject);
        processing(sender, sendertDict, mailObject);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<T>> getMailBox() {
        return recipientDict;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return Collections.max(sendertDict.entrySet(), Comparator.comparingInt((Map.Entry<String, List<T>> e) -> e.getValue().size())).getKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {

        return Collections.max(recipientDict.entrySet(), Comparator.comparingInt((Map.Entry<String, List<T>> e) -> e.getValue().size())).getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public void process(MailService<T> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }

    private void processing(String key, Map<String, List<T>> mailMap, T mailObject) {
        List<T> value = mailMap.get(key);
        if (value != null) {
            value.add(mailObject);
            mailMap.put(key, value);
        } else {
            List<T> newValue = new ArrayList<>();
            newValue.add(mailObject);
            mailMap.put(key, newValue);
        }
    }
}

