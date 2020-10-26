package ru.mail.polis.homework.collections.mail;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer {
    Map<String, Integer> sendlers = new HashMap<>();
    Map<String, Integer> recipient = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List> getMailBox() {
        return null;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        if ((sendlers.isEmpty())){
            return "";
        }
        return sendlers.entrySet().stream()
                .max(Map.Entry.<String, Integer>comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        if(recipient.isEmpty() ){
            return "";
        }
        return recipient.entrySet().stream()
                .max(Map.Entry.<String, Integer>comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {

    }
}
