package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<M extends MailMessage<?>> implements Consumer<M> {
    private final PopularMap<Mail<M>, Mail<M>> messages = new PopularMap<>(); //словарь сообщений, где ключи - отправители, а значения - получатели


    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */

    public void accept(M message) {
        messages.put(message.getSender(), message.getRecipient());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<M>> getMailBox() {
        return messages.values().stream()
                .collect(Collectors.toMap(Mail::getMailbox, Mail<M>::getReceivedMessages));
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return messages.getPopularKey().getMailbox();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return messages.getPopularValue().getMailbox();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <M extends MailMessage<?>> void process(MailService<M> service, List<M> mails) {
        mails.forEach(service);
    }
}
