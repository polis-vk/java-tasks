package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService <T extends Mail> implements Consumer<T> {

    private final Map<String, List<T>> mailBox;
    private final PopularMap<String, String> popMap;

    public MailService() {
        this.mailBox = new HashMap<String, List<T>>();
        this.popMap = new PopularMap<String, String>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        /* Более прилично выглядящий вариант
          Минус - 2 обращения к методам mailBox
         */
        mailBox.putIfAbsent(mail.getReceiver(), new ArrayList<T>());
        mailBox.get(mail.getReceiver()).add(mail);
        popMap.put(mail.getSender(), mail.getReceiver());

        /* Менее приличный вариант
        + - одно обращение к методу mailbox
        - - Мы в одной строке объявляем список, добавляем список в карту, инициализируя его с помощью создания списка из массива из одного элемента
        List<T> list = mailBox.putIfAbsent(mail.getReceiver(), new ArrayList<T>(Arrays.asList(mail)));
        if(list!=null) {
            list.add(mail);
        }
        popMap.put(mail.getSender(), mail.getReceiver());
        */

        /* Старый способ добавления элемента
        if (!mailBox.containsKey(mail.getReceiver())) {
            mailBox.put(mail.getReceiver(), new ArrayList<Mail>());
        }
        mailBox.get(mail.getReceiver()).add(mail);

        popMap.put(mail.getSender(), mail.getReceiver());
        */

        /* Я не знаю, зачем я это оставил, но оно выглядит весело.
        try {
            mailBox.putIfAbsent(mail.getReceiver(), new ArrayList<Mail>()).add(mail);
        }
        catch(NullPointerException e){
            mailBox.get(mail.getReceiver()).add(mail);
        }
         */
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return popMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return popMap.getPopularValue();
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
