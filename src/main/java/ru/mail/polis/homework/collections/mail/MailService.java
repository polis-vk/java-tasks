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
 */
public class MailService<T extends Message<T>> implements Consumer<Message<T>> {
    private final PopularMap<String, List<Message<T>>> senders;
    private final PopularMap<String, List<Message<T>>> recipients;

    public MailService(){
        senders = new PopularMap<>();
        recipients = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Message<T> message) {
        processMassage(message);
    }

    public void processMassage(Message<T> massage){
        List<Message<T>> senderList = new ArrayList<>();
        senderList = senders.getOrDefault(massage.getSender(), senderList);
        senderList.add(massage);
        senders.put(massage.getSender(), senderList);

        List<Message<T>> recipientList = new ArrayList<>();
        recipientList = senders.getOrDefault(massage.getRecipient(), senderList);
        recipientList.add(massage);
        recipients.put(massage.getRecipient(), recipientList);
    }
    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Message<T>>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя>
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
    public static void process(MailService service, List<Message> mails) {
        mails.forEach(service);
    }
}
