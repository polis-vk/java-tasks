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
public class MailService<M extends Message<?>> implements Consumer<M> {

    private final PopularMap<String, List<M>> recipientBox = new PopularMap<>();
    private final PopularMap<String, List<M>> sendersBox = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(M message) {
        recipientBox.computeIfAbsent(message.getRecipient(), (v) -> new ArrayList<>()).add(message);
        sendersBox.computeIfAbsent(message.getSender(), (v) -> new ArrayList<>()).add(message);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<M>> getMailBox() {
        return recipientBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return sendersBox.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return recipientBox.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <M extends Message<?>> void process(MailService<M> service, List<M> mails) {
        for (M mail : mails) {
            service.accept(mail);
        }
    }
}
