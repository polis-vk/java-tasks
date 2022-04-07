package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Collections;
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
 * Всего 7 баллов за пакет mail
 */
public class MailService implements Consumer<Mail<?>> {

    private final Map<String, List<Mail<?>>> recipients;
    private final PopularMap<String, String> popularityMap;

    public MailService() {
        recipients = new HashMap<>();
        popularityMap = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail<?> mail) {
        recipients.computeIfAbsent(mail.getRecipient(), e -> new ArrayList<>()).add(mail);
        popularityMap.put(mail.getSender(), mail.getRecipient());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List> getMailBox() {
        return Collections.unmodifiableMap(recipients);
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return popularityMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return popularityMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService service, List<? extends Mail<?>> mails) {
        mails.forEach(service);
    }
}
