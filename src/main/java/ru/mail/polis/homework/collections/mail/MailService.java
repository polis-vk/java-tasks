package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.mail.mailsystem.AbstractSentReceivedObject;
import ru.mail.polis.homework.collections.mail.person.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends AbstractSentReceivedObject> implements Consumer<List<T>> {

    private Map<Person, List<AbstractSentReceivedObject<?>>> receivers;
    private Map<Person, List<AbstractSentReceivedObject<?>>> senders;

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
        for (AbstractSentReceivedObject<?> object : o) {

            Person receiver = object.getReceiver();

            senders.computeIfAbsent(object.getSender(), key -> new ArrayList<>()).add(object);
            receivers.computeIfAbsent(receiver, key -> new ArrayList<>()).add(object);
            receiver.receive(object.getSender().send(object));
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<Person, List<AbstractSentReceivedObject<?>>> getMailBox() {
        return receivers;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public Person getPopularSender() {
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
    public Person getPopularRecipient() {
        return receivers
            .entrySet()
            .stream()
            .max(Comparator.comparing(entry -> entry.getValue().size()))
            .get()
            .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(
        MailService<AbstractSentReceivedObject<?>> service,
        List<AbstractSentReceivedObject<?>> mails
    ) {
        service.accept(mails);
    }
}
