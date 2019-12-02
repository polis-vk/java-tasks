package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<Mail> {

    Map<String, List<Mail>> mailBox = new HashMap<>();
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail o) {
      mailBox.computeIfPresent(o.getReciever(), (key, value) -> {
        List<Mail> list = mailBox.remove(key);
        list.add(o);
        return list;
      });
      mailBox.computeIfAbsent(o.getReciever(), (key) -> {
        List<Mail> list = new ArrayList<>();
        list.add(o);
        return list;
      });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
      return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
      return mailBox
              .entrySet()
              .stream()
              .flatMap(elem -> elem.getValue().stream())
              .collect(Collectors.groupingBy(Mail::getSender, Collectors.counting()))
              .entrySet()
              .stream()
              .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
              .get()
              .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
      return mailBox
              .entrySet()
              .stream()
              .flatMap(elem -> elem.getValue().stream())
              .collect(Collectors.groupingBy(Mail::getReciever, Collectors.counting()))
              .entrySet()
              .stream()
              .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
              .get()
              .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
      mails.forEach(service);
    }
}
