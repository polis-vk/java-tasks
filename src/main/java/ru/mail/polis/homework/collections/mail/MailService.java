package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<T> implements Consumer<Mail<T>> {
    Map<String, List<Mail<T>>> mailBox = new HashMap<>();
    PopularMap<String, String> popularMap = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Mail<T> mail) {
        if (!mailBox.containsKey(mail.getRecipient())) {
            mailBox.put(mail.getRecipient(), new ArrayList<>());
        }
        mailBox.get(mail.getRecipient()).add(mail);
        popularMap.put(mail.getSender(), mail.getRecipient());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<Mail<T>>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return popularMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return popularMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T> void process(MailService<T> service, List<Mail<T>> mails) {
        mails.forEach(service);
    }
}
