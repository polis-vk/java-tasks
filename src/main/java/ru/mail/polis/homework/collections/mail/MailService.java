package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.mail.transmissions.TrasmissionObject;
import ru.mail.polis.homework.collections.mail.users.User;

import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends TrasmissionObject> implements Consumer<List<T>> {

    private Map<User, List<TrasmissionObject<?>>> receivers;
    private Map<User, List<TrasmissionObject<?>>> senders;

    public MailService() {
        receivers = new HashMap<>();
        senders = new HashMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(List<T> o) {
        for (TrasmissionObject<?> object : o) {
            User receiver = object.getReceiver();
            senders.computeIfAbsent(object.getSender(), key -> new ArrayList<>()).add(object);
            receivers.computeIfAbsent(receiver, key -> new ArrayList<>()).add(object);
            receiver.receive(object.getSender().send(object));
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<User, List<TrasmissionObject<?>>> getMailBox() {
        return receivers;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public User getPopularSender() {
        return senders
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public User getPopularRecipient() {
        return receivers
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(
            MailService<TrasmissionObject<?>> service,
            List<TrasmissionObject<?>> mails) {

        service.accept(mails);
    }
}
