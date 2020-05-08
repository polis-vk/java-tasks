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
 */
public class MailService<V extends Mail<?>> implements Consumer<V> {
    private final PopularMap<String, List<V>> senders;
    private final PopularMap<String, List<V>> recipients;

    public MailService() {
        this.senders = new PopularMap<>();
        this.recipients = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(V message) {
        addNewMessage(senders, message, message.getSender());
        addNewMessage(recipients, message, message.getRecipient());
    }

    private void addNewMessage(Map<String, List<V>> map, V message, String user) {
        map.computeIfAbsent(user, k -> new ArrayList<>()).add(message);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<V>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<? extends Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }
}
