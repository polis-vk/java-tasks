package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ru.mail.polis.homework.collections.PopularMap;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<V> implements Consumer<Mail<MailMessage<V>>> {

    private final PopularMap<String, List<Mail<MailMessage<V>>>> addressees;
    private final PopularMap<String, List<Mail<MailMessage<V>>>> senders;

    public MailService() {
        this.addressees = new PopularMap<>();
        this.senders = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Mail<MailMessage<V>> mail) {
        senders.computeIfAbsent(mail.getSender(), mails -> new ArrayList<>()).add(mail);
        addressees.computeIfAbsent(mail.getAddressee(), mails -> new ArrayList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<Mail<MailMessage<V>>>> getMailBox() {
        return addressees;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return addressees.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }
}
