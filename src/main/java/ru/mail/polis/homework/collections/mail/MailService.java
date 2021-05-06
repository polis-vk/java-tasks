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
public class MailService<T extends MailTemplate> implements Consumer<T> {
    private final PopularMap<String, List<T>> senders;
    private final PopularMap<String, List<T>> recipients;

    public MailService() {
        this.senders = new PopularMap<>();
        this.recipients = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        String sender = mail.getSender();
        this.senders.computeIfAbsent(sender, k -> new ArrayList<T>()).add(mail);

        String recipient = mail.getRecipient();
        this.recipients.computeIfAbsent(recipient, k -> new ArrayList<T>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return this.recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return this.senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return this.recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends MailTemplate> void process(MailService<T> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }
}
