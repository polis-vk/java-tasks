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
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {
    private final PopularMap<String, List<T>> recievers;
    private final PopularMap<String, Integer> sources;

    public MailService() {
        this.recievers = new PopularMap<>();
        this.sources = new PopularMap<>();
    }

    public MailService(PopularMap<String, List<T>> recievers, PopularMap<String, Integer> sources) {
        this.recievers = recievers;
        this.sources = sources;
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T message) {
        recievers.computeIfAbsent(message.getDestination(), k -> new ArrayList<>()).add(message);
        sources.merge(message.getSource(), 1, (oldValue, newValue) -> oldValue + newValue);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return recievers;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return sources.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return recievers.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends Mail<?>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(service);
    }
}
