package ru.mail.polis.homework.collections.mail;


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
public class MailService implements Consumer {

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {

    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List> getMailBox() {
        return null;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return null;
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return null;
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService service, List mails) {

    }
}
