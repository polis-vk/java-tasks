package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService <G extends Mail> implements Consumer<G> {

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(G g) {
        senders.computeIfAbsent(g.getSender(), k -> new ArrayList<>()).add(g);
        addressee.computeIfAbsent(g.getAddressee(), k -> new ArrayList<>()).add(g);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * @return
     */
    public PopularMap<String, List<Mail>> getMailBox() { return addressee; }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() { return senders.getPopularKey(); }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() { return addressee.getPopularKey(); }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) { mails.forEach(service); }

    private PopularMap<String, List<Mail>> senders = new PopularMap<>();
    private PopularMap<String, List<Mail>> addressee = new PopularMap<>();


}
