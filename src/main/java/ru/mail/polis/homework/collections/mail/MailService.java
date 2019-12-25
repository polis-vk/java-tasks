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
public class MailService<T extends Subject<T>> implements Consumer<T> {

    final private PopularMap<String, List<Subject<T>>> sender_;
    final private PopularMap<String, List<Subject<T>>> receiver_;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */

    public MailService() {
        sender_ = new PopularMap<>();
        receiver_ = new PopularMap<>();
    }

    @Override
    public void accept(T o) {
        final List<Subject<T>> senderList;
        if (sender_.containsKey(o.getSender_())) {
            senderList = sender_.get(o.getSender_());
        } else {
            senderList = new ArrayList<>();
        }
        senderList.add(o);
        sender_.put(o.getSender_(), senderList);

        final List<Subject<T>> receiverList;
        if (receiver_.containsKey(o.getReceiver_())) {
            receiverList = receiver_.get(o.getReceiver_());
        } else {
            receiverList = new ArrayList<>();
        }
        receiverList.add(o);
        receiver_.put(o.getReceiver_(), receiverList);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Subject<T>>> getMailBox() {
        return receiver_;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return sender_.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return receiver_.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
        mails.forEach(service);
    }
}
