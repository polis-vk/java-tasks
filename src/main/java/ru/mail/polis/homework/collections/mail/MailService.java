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
 */   //PECS
public class MailService<M, T extends Mail<M>> implements Consumer<T> {

    private final PopularMap<String, List<T>> addressee;
    private final PopularMap<String, List<T>> senders;

    public MailService() {
        this.addressee = new PopularMap();
        this.senders = new PopularMap();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T sent) {
        manageMail(addressee, sent, sent.getAddressee());
        manageMail(senders, sent, sent.getSender());
    }

    private void manageMail(Map<String, List<T>> map, T message, String sender) {
        map.computeIfAbsent(sender, s -> new ArrayList<>()).add(message);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return addressee;
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
    public String getPopularAddressee() {
        return addressee.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<? extends Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }
}
