package ru.mail.polis.homework.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mail.polis.homework.collections.mail.Mail;
import ru.mail.polis.homework.collections.mail.MailMessage;
import ru.mail.polis.homework.collections.mail.MailService;
import ru.mail.polis.homework.collections.mail.Salary;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

  MailService mailService = new MailService();

  @Test
  public void accept() {
    mailService.accept(new MailMessage("1", "2", "Some text"));
    Map<String, List<Mail>> mailBox = mailService.getMailBox();
    assertTrue(mailBox.containsKey("2"));
    mailService.accept(new Salary("3", "2", 500));
    assertTrue(mailBox.containsKey("2"));
    mailBox = mailService.getMailBox();
    assertEquals(2, mailBox.get("2").size());
  }
}