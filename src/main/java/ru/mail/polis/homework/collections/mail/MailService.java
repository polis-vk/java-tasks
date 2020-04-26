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
public class MailService<T extends SimpleMessage<?>> implements Consumer<T> {
     private final PopularMap<String, List<T>> recipients = new PopularMap<>();
    private final PopularMap<String, List<T>> senders = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T t) {
        updateUser(t, recipients, t.getRecipient());
        updateUser(t, senders, t.getSender());
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<SimpleMessage<?>> service, List<? extends SimpleMessage<?>> mails) {
        for (SimpleMessage<?> mail : mails) {
            service.accept(mail);
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
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

    private void updateUser(T t, PopularMap<String, List<T>> updateProperty, String client) {
        List<T> messages = updateProperty.getOrDefault(client, new ArrayList<>());
        messages.add(t);
        updateProperty.put(client, messages);
    }
}
