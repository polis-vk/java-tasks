package ru.mail.polis.homework.collections.mail;


import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * ����� ������� ������, ������� ����� ������������ ������ � ��������.
 * ������ ������� �� ����������, �����������, ������ ���������
 * �������� ������� �� ����������, ����������� � �����.
 *
 * � ���������� ����� �� ������ ���� ������� Object � ��������� ��� ����. ����������� ���������.
 * ����� 7 �������� �� ����� mail
 */
public class MailService implements Consumer {

    /**
     * � ������� ����� ������ �������� ������ ������������ ������ � ��������
     * 1 ������
     */
    @Override
    public void accept(Object o) {

    }

    /**
     * ����� ���������� ���� ���������� -> ��� ������� ������� ������ � ����� ���������� ����� ������ �������� ������
     * 1 ������
     */
    public Map<String, List> getMailBox() {
        return null;
    }

    /**
     * ���������� ������ ����������� �����������
     * 1 ������
     */
    public String getPopularSender() {
        return null;
    }

    /**
     * ���������� ������ ����������� ����������
     * 1 ������
     */
    public String getPopularRecipient() {
        return null;
    }

    /**
     * ����� ������ ��������� ���������� service ��� mails.
     * 1 ������
     */
    public static void process(MailService service, List mails) {

    }
}
