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
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends SimpleMessage> implements Consumer<T> {
     private final PopularMap<String, List<SimpleMessage>> recipients = new PopularMap<>();
    private final PopularMap<String, List<SimpleMessage>> senders = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T t) {
        updateRecipients(t, recipients, t.getRecipient());
        updateRecipients(t, senders, t.getSender());
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<SimpleMessage> mails) {
        for (SimpleMessage mail : mails) {
            service.accept(mail);
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<SimpleMessage>> getMailBox() {
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

    private void updateRecipients(T t, PopularMap<String, List<SimpleMessage>> updateProperty, String client) {
        List<SimpleMessage> messages;
        if (updateProperty.containsKey(client)) {
            messages = updateProperty.get(client);
        } else {
            messages = new ArrayList<>();
        }
        messages.add(t);
        updateProperty.put(client, messages);
    }
}
