package ru.mail.polis.homework.collections.mail;

import java.util.LinkedList;

public class test {
    public static void main(String[] args) {
        MailService service = new MailService();

        MailMessage message1 = new MailMessage("I1","He2","hello");
        MailMessage message2 = new MailMessage("I3","He2","hello,how are you");
        MailMessage message3 = new MailMessage("I2","He1","hello,friend");

        Salary zp1 = new Salary("I1","He3",100);
        Salary zp2 = new Salary("I2","He2",200);
        Salary zp3 = new Salary("I2","He1",300);

        LinkedList<MailMessage> mes = new LinkedList<>();
        mes.add(message1);
        mes.add(message2);
        mes.add(message3);

        LinkedList<Salary> salary = new LinkedList<>();
        salary.add(zp1);
        salary.add(zp2);
        salary.add(zp3);

        service.process(service, mes);
        service.process(service, salary);

        System.out.println("Самый популярный отправитель: "+service.getPopularSender());
        System.out.println("Самый популярный получатель: "+service.getPopularRecipient());
        System.out.println("Список всех получателей: "+service.getMailBox().keySet());
    }
}
