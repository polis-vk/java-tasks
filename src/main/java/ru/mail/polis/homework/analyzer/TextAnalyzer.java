package ru.mail.polis.homework.analyzer;


/**
 * ������� ��������� �������, ���������� ����� ���������� ������ ��������������� � ���� ��� ������
 * ������� ������.
 * ���� ���� ����������� ������, ������� ������� ������� ��������� ���� (�� ��� ��� ���������� ���������, ��� ��� ���
 * ����� ���, ��� ��� �� ����� ���������� ����������, � ����� ������ ��������, ��� ��������� ����������� �������
 * ����� ���-�� ������������� �����). ���� ����������� ������ ��� ����� ��� �������� ������,
 * ��� �� ��� �������� ������� (������� �� ��������) � ���� "������������" �� ��������.
 *
 * ��� �� ���������� ������� ��� ���������� ������, ������� ����� ��� ����� ��� ������� ������
 * �� ���� �������� � ������ TextFilterManager
 *
 * 2 ������� + (2 ������� �� ������ ������ + 1 ������ �� ���� �� ���� ������) ����� 11
 */
public interface TextAnalyzer {

    static TextAnalyzer createTooLongAnalyzer(long maxLength) {
        return null;
    }

    static TextAnalyzer createSpamAnalyzer(String[] spam) {
        return null;
    }

    static TextAnalyzer createNegativeTextAnalyzer() {
        return null;
    }

    /**
     * ��������� ���� ������
     */
    static <T> TextAnalyzer createCustomAnalyzer(T something) {
        return null;
    }
}
