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
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {
    //можно получить контент типа Object...
    private final PopularMap<String, List<T>> recipientMail = new PopularMap<>();
    private final PopularMap<String, List<T>> senderMail = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T tMail) { // всяко лучше чем было XD
        List<T> listRecipient = recipientMail.getOrDefault(tMail.getRecipient(), new ArrayList<>());
        listRecipient.add(tMail);
        recipientMail.put(tMail.getRecipient(), listRecipient);

        List<T> listSender = senderMail.getOrDefault(tMail.getSender(), new ArrayList<>());
        listSender.add(tMail);
        senderMail.put(tMail.getSender(), listSender);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return recipientMail;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senderMail.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return recipientMail.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Mail<?>> service, List<Mail<?>> mails) {
        for (Mail<?> m : mails) {
            service.accept(m);
        }
    }
}






