package ru.mail.polis.homework.collections.mail;

import ru.mail.polis.homework.retake.first.PopularMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<Ty extends Mail<?>> implements Consumer<Ty> {
    private final PopularMap<String, String> sendersAndRecipientsMap;
    private final Map<String, List<Ty>> mails;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    public MailService() {
        sendersAndRecipientsMap = new PopularMap<>();
        mails = new HashMap<>();
    }

    @Override
    public void accept(Ty mail) {
        sendersAndRecipientsMap.put(mail.getSender(), mail.getRecipient());
        mails.computeIfAbsent(mail.getSender(), recipient -> new ArrayList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<Ty>> getMailBox() {
        return mails;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return sendersAndRecipientsMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return sendersAndRecipientsMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <Ty extends Mail<Ty>> void process(MailService<Ty> service, List<Ty> mails) {
        mails.forEach(service);
    }
}
