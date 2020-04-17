package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.lang.reflect.Array;
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
public class MailService implements Consumer {
    Map<String, List> consumerMail;
    PopularMap<String, String> popularConsumer = new PopularMap<String, String>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {
        Mail mail = ((Mail) o);
        if (consumerMail.containsKey(mail.receiver)) {
            consumerMail.get(mail.receiver).add(mail);
        } else {
            consumerMail.put(mail.receiver, new ArrayList() {
            });
        }
        popularConsumer.put(mail.sender,mail.receiver);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List> getMailBox() {
        return consumerMail;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return popularConsumer.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return popularConsumer.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
        for (Object mail : mails) {
            service.accept(mail);
        }
    }
}
