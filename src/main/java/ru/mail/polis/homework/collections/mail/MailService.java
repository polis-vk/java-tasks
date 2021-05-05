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
public class MailService<T extends Incoming<?>> implements Consumer<T> {
    private final PopularMap<String, List<T>> receiverMap = new PopularMap<>();
    private final PopularMap<String, Integer> senderMap = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T o) {
        senderMap.merge(o.getSender(), 1, Integer::sum);
        receiverMap.merge(o.getReceiver(), new ArrayList<>(Arrays.asList(o)), (oldVal, newVal) -> {
            newVal.addAll(oldVal);
            return newVal;
        });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return receiverMap;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return senderMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        PopularMap<String, Integer> numberOfReceiversMessage = new PopularMap<>();
        receiverMap.entrySet().forEach(x -> numberOfReceiversMessage.put(x.getKey(), x.getValue().size()));
        return numberOfReceiversMessage.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends Incoming<?>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(service);
    }
}
