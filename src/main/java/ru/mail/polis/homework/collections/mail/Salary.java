package ru.mail.polis.homework.collections.mail;


public class Salary extends AbstractMailMessage<Integer>{
    public Salary(String from, String to, Integer salary){
        super(from, to, salary);
    }
}
