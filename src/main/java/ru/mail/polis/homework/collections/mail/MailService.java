package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.math.BigDecimal;
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
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {
    private final PopularMap<String, List<T>> recipient;
    private final PopularMap<String, List<T>> sender;

    public MailService(PopularMap<String, List<T>> receiver, PopularMap<String, List<T>> sender) {
        this.recipient = receiver;
        this.sender = sender;
    }

    public MailService() {
        this.recipient = new PopularMap<>();
        this.sender = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        List<T> list = new ArrayList<T>();
        list.add(mail);
        recipient.put(mail.getMailRecipient(), list);
        sender.put(mail.getMailSender(), list);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return recipient;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return sender.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return recipient.getPopularKey();
    }

    // это метод спецслужб
    private void readMails(List<Mail<?>> mails) {
        mails
            .forEach(System.out::print);
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Mail<?>> service, List<Mail<?>> mails) {
        service.readMails(mails);
        for (Mail<?> mail : mails) {
            service.accept(mail);
        }

    }
}
