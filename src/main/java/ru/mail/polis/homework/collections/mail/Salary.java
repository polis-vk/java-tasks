package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
  private long sum;

  public Salary(String sender, String reciever, long sum) {
    super(sender, reciever);
    this.sum = sum;
  }
}
