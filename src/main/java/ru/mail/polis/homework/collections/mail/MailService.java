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
public class MailService<T extends MailSomething> implements Consumer<T> {

    private final PopularMap<String, List<T>> receiver;
    private final PopularMap<String, List<T>> sender;

    public MailService(PopularMap<String, List<T>> receiver, PopularMap<String, List<T>> sender) {
        this.receiver = receiver;
        this.sender = sender;
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T sentThing) {
        ArrayList<T> lst = new ArrayList<T>();
        lst.add(sentThing);
        receiver.put(sentThing.getMailReceiver(), lst);
        sender.put(sentThing.getMailSender(), lst);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {

        return receiver;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {

        return receiver.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {

        return sender.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<MailSomething> mails) {
        for (MailSomething mail : mails) {
            service.accept(mail);
        }
    }
}
