package ru.mail.polis.homework.collections.mail;

abstract public class Mail {
  private String sender;
  private String reciever;

  public Mail(String sender, String reciever) {
    this.sender = sender;
    this.reciever = reciever;
  }

  public String getSender() {
    return sender;
  }

  public String getReciever() {
    return reciever;
  }
}
