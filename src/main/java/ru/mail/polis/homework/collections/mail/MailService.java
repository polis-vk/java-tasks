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
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends MessageContent<T>> implements Consumer<T> {
    private final PopularMap<String, List<T>> senders;
    private final PopularMap<String, List<T>> recipients;

    public MailService(PopularMap<String, List<T>> sender, PopularMap<String, List<T>> recipient) {
        this.senders = new PopularMap<>();
        this.recipients = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        processClient(mail, mail.getRecipient(), recipients);
        processClient(mail, mail.getSender(), senders);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     * @return
     */
    public Map<String, List<T>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public void process(MailService service, List<? extends MessageContent<T>> mails) {
        for (MessageContent mail : mails) {
            service.accept(mail);
        }
    }

    private void processClient(T mail, String client, PopularMap<String, List<T>> clients) {
        List<T> mails = clients.getOrDefault(client, new ArrayList<>());
        mails.add(mail);
        clients.put(client, mails);
    }
}
