package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * ������� ����������� � 2 �����.
 * ���� �� ����� ���������� �����.
 * ���� ��������� ������ ������� ����. ���� ����������� ����� positionPartString
 * ������� ������: ������� ��� ��������� ���������, �� ������� ����� ���������
 * ���������� �����. ��� �� �������� ��� �����, ������� �������� � ���������, ����������
 * �������� ������� ��� ������ � ���.
 * ������������ ����� ������ �� �(n).
 */
public class SearchInTheShredderList {
    private List<String> partStrings = new ArrayList<>();

    public SearchInTheShredderList() {
    }

    public SearchInTheShredderList(List<String> partStrings) {
        this.partStrings = partStrings;
    }

    public void add(String value) {
        partStrings.add(value);
    }

    public String get(int index) {
        return partStrings.get(index);
    }

    /**
     * ���� ������� �������� �� ������� ����� ��������� ������������ �����
     *
     * @param value - ������������� �����
     * @return - ���� ������ � ��������� ��������� �������� ���� �����, ���� - null
     */
    public int[] positionPartString(String value) {
        return null;
    }
}
