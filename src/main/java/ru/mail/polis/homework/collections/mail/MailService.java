package ru.mail.polis.homework.collections.mail;

import ru.mail.polis.homework.collections.PopularMap;

import java.util.LinkedList;
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
public class MailService<T extends AbstractMailMessage> implements Consumer<T> {

    private PopularMap<String, List<AbstractMailMessage>> senders = new PopularMap<>();
    private PopularMap<String, List<AbstractMailMessage>> receivers = new PopularMap<>();
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T o) {
        senders.computeIfAbsent(o.getFrom(), val -> new LinkedList<>()).add(o);
        receivers.computeIfAbsent(o.getTo(), val -> new LinkedList<>()).add(o);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<AbstractMailMessage>> getMailBox() {
        return receivers;
    }

    /**
     * Метод возвращает лист всех сообщений для конкретного получателя
     */
    public List<AbstractMailMessage> getMailBox(String receiver) {
        return receivers.get(receiver);
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return receivers.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<AbstractMailMessage> mails) {
        mails.forEach(mail -> service.accept(mail));
    }
}
