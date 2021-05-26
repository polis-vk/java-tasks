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
public class MailService<T extends Mail<T>> implements Consumer<T> {
    private final PopularMap<String, List<T>> senders;
    private final PopularMap<String, List<T>> receivers;

    public MailService() {
        senders = new PopularMap<>();
        receivers = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        addMail(mail.getSender(), mail, senders);
        addMail(mail.getReceiver(), mail, receivers);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return receivers;
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

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends Mail<T>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(mail -> service.accept(mail));
    }

    private void addMail(String person, T mail, PopularMap<String, List<T>> map) {
        map.computeIfAbsent(person, s -> new ArrayList<>()).add(mail);
    }
}
