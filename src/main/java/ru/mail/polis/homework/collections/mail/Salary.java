package ru.mail.polis.homework.collections.mail;

public class Salary extends Message<Integer>{
    public Salary(String recipient, String sender, Integer sum){
        super(recipient,sender,sum);
    }
}
