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
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends MessagingUnit> implements Consumer<T> {

    PopularMap<String, List<T>> senders;
    PopularMap<String, List<T>> receivers;

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends MessagingUnit> void process(MailService<T> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T unit) {
        if (senders.containsKey(unit.getSender())) {
            senders.get(unit.getSender()).add(unit);
        } else {
            List<T> newList = new ArrayList<T>(1);
            newList.add(unit);
            senders.put(unit.getSender(), newList);
        }

        if (receivers.containsKey(unit.getReceiver())) {
            receivers.get(unit.getReceiver()).add(unit);
        } else {
            List<T> newList = new ArrayList<T>(1);
            newList.add(unit);
            receivers.put(unit.getReceiver(), newList);
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return senders;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return receivers.getPopularKey();
    }

}
