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
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends InboxMessage<?>> implements Consumer<T> {

    private final PopularMap<String, List<T>> addressee = new PopularMap<>();
    private final PopularMap<String, List<T>> sender = new PopularMap<>();


    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     *
     * @param o
     */

    @Override
    public void accept(T o) {
        addressee.computeIfAbsent(o.getAddressee(), (a) -> new ArrayList<>()).add(o);
        sender.computeIfAbsent(o.getSender(), (a) -> new ArrayList<>()).add(o);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return addressee;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return sender.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return addressee.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends InboxMessage<?>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(service::accept);
    }

}
