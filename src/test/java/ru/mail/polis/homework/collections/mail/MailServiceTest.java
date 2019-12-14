package ru.mail.polis.homework.collections.mail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MailServiceTest {
    MailService mailService;
    private Map<String, List<Mail>> recipients;
    private Map<String, List<Mail>> senders;

    @Before
    public void setUp() {
        mailService = new MailService();
        recipients = new HashMap<>();
        senders = new HashMap<>();

        MailMessage mail1 = new MailMessage("Almaz", "Alexander", "Sorry for breaking deadlines, check my HW please :3");
        MailMessage mail2 = new MailMessage("Alexander", "Almaz", "No problem, bro");
        MailMessage mail3 = new MailMessage("Santa Claus", "Any", "Happy new year! Ho-ho-ho!");
        MailMessage mail4 = new MailMessage("Teacher", "Almaz", "Your test result is 59/60");

        recipients.computeIfAbsent(mail1.getRecipient(), x -> new ArrayList<>()).add(mail1);
        recipients.computeIfAbsent(mail2.getRecipient(), x -> new ArrayList<>()).add(mail2);
        recipients.computeIfAbsent(mail3.getRecipient(), x -> new ArrayList<>()).add(mail3);
        recipients.computeIfAbsent(mail4.getRecipient(), x -> new ArrayList<>()).add(mail4);

        senders.computeIfAbsent(mail1.getSender(), x -> new ArrayList<>()).add(mail1);
        senders.computeIfAbsent(mail2.getSender(), x -> new ArrayList<>()).add(mail2);
        senders.computeIfAbsent(mail3.getSender(), x -> new ArrayList<>()).add(mail3);
        senders.computeIfAbsent(mail4.getSender(), x -> new ArrayList<>()).add(mail4);

        mailService.accept(mail1);
        mailService.accept(mail2);
        mailService.accept(mail3);
        mailService.accept(mail4);




        Salary salary1 = new Salary("Pension Fund", "Babushka", 15000);
        Salary salary2 = new Salary("OK", "Timur", 200000);
        Salary salary3 = new Salary("OK", "Alexander", 200000);
        Salary salary4 = new Salary("Polytech", "Almaz", 2200);

        recipients.computeIfAbsent(salary1.getRecipient(), x -> new ArrayList<>()).add(salary1);
        recipients.computeIfAbsent(salary2.getRecipient(), x -> new ArrayList<>()).add(salary2);
        recipients.computeIfAbsent(salary3.getRecipient(), x -> new ArrayList<>()).add(salary3);
        recipients.computeIfAbsent(salary4.getRecipient(), x -> new ArrayList<>()).add(salary4);

        senders.computeIfAbsent(salary1.getSender(), x -> new ArrayList<>()).add(salary1);
        senders.computeIfAbsent(salary2.getSender(), x -> new ArrayList<>()).add(salary2);
        senders.computeIfAbsent(salary3.getSender(), x -> new ArrayList<>()).add(salary3);
        senders.computeIfAbsent(salary4.getSender(), x -> new ArrayList<>()).add(salary4);

        mailService.accept(salary1);
        mailService.accept(salary2);
        mailService.accept(salary3);
        mailService.accept(salary4);
    }

    @Test
    public void accept() {
        Map<String, List<Mail>> recipients2 = mailService.getMailBox();
        assertEquals(1, recipients2.get("Babushka").size());
        assertEquals(3, recipients2.get("Almaz").size());
        assertEquals(2, recipients2.get("Alexander").size());
        assertEquals(1, recipients2.get("Timur").size());
    }

    @Test
    public void getMailBox() {
        assertEquals(recipients, mailService.getMailBox());
    }

    @Test
    public void getPopularSender() {
        assertEquals("OK", mailService.getPopularSender());
    }

    @Test
    public void getPopularRecipient() {
        assertEquals("Almaz", mailService.getPopularRecipient());
    }

    @Test
    public void process() {
        Mail mail = new MailMessage("Sender", "Recipient", "Message");
        Mail salary = new Salary("Logitech", "HP", 1000000);
        List<Mail> list = new ArrayList<>();
        list.add(mail);
        list.add(salary);
        MailService.process(mailService, list);
        Map<String, List<Mail>> recipients2 = mailService.getMailBox();
        assertTrue(recipients2.get("Recipient").contains(mail));
        assertTrue(recipients2.get("HP").contains(salary));
    }
}