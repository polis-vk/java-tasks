package ru.mail.polis.homework.collections.mail;

import com.sun.tools.javac.util.List;

class MailMain {

    public static void main(String[] args) {
        MailService service = new MailService();

        Message message1 = new Message("John","Jim","Hello. Problems with access to JoyCasino?");
        Message message2 = new Message("Jim","John","Who are me?");
        Message message3 = new Message("Jim","John","Don't text ever again!");

        Salary salary1 = new Salary("Google","Bob",100);
        Salary salary2 = new Salary("Google","Bambina",200);
        Salary salary3 = new Salary("Microsoft","Lucy",300);

        List<Message> mes = List.of(message1, message2, message3);
        List<Salary> salary = List.of(salary1, salary2, salary3);

        service.process(service, mes);
        service.process(service, salary);

        System.out.println("Самый популярный отправитель: " + service.getPopularSender());
        System.out.println("Самый популярный получатель: " + service.getPopularRecipient());
        System.out.println("Список всех получателей: " + service.getMailBox().keySet());
    }
}
