package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends Mail> implements Consumer<T> {

    private final PopularMap<String, List<Mail>> recipients;
    private final PopularMap<String, List<Mail>> senders;

    public MailService() {
        recipients = new PopularMap<>();
        senders = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        acceptCorrespondent(mail, recipients, mail.getRecipient());
        acceptCorrespondent(mail, senders, mail.getSender());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<Mail>> getMailBox() {
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
    public static void process(MailService service, List<Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }

    private void acceptCorrespondent(T mail, PopularMap<String, List<Mail>> map, String correspondent) {
        map.computeIfAbsent(correspondent, k -> new ArrayList<>()).add(mail);
    }
}
