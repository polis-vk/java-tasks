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
public class MailService<T extends MailItem<?>> implements Consumer<T> {
    private final PopularMap<String, List<T>> sendersMap = new PopularMap<>();
    private final PopularMap<String, List<T>> receiversMap = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T t) {
        sendersMap.getOrDefault(t.getSender(), new ArrayList<>()).add(t);
        receiversMap.getOrDefault(t.getReceiver(), new ArrayList<>()).add(t);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return sendersMap;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return sendersMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return receiversMap.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static <T extends MailItem<?>> void process(MailService<T> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }

}