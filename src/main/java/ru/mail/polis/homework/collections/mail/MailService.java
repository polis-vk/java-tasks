package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {

    private final PopularMap<String, List<T>> senders = new PopularMap<>();
    private final PopularMap<String, List<T>> recipients = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T o) {
        add(o.getSender(), o, senders);
        add(o.getRecipient(), o, recipients);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return recipients;
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
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService<Mail<?>> service, List<? extends Mail<?>> mails) {
        mails.forEach(service);
    }

    private void add(String name, T mail, PopularMap<String, List<T>> map) {
        List<T> newMail = new ArrayList<T>() {{add(mail);}};
        List<T> currentMails = map.putIfAbsent(name, newMail);
        if (!currentMails.equals(newMail)) {
            currentMails.add(mail);
        }
    }
}
