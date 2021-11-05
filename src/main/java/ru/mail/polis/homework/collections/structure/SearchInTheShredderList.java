package ru.mail.polis.homework.collections.structure;

import java.util.*;

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

    private static final int NO_POSITION = -1;
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
     * ��������� - [n + k^2 => O(n) ��������������� (����������: k = n),
     * k - ���������� ��������� ������� ����� value ����� ShredderList.size() ��������]
     * */
    public int[] positionPartString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        int[] positions = new int[]{NO_POSITION, NO_POSITION};
        for (int i = 0; i < partStrings.size(); ++i) {
            String curPartOfWord = partStrings.get(i);
            if (value.startsWith(curPartOfWord)) {
                positions[0] = i;
            } else if (value.endsWith(curPartOfWord)) {
                positions[1] = i;
            }
            if (positions[0] != NO_POSITION && positions[1] != NO_POSITION) {
                break;
            }
        }
        if (positions[0] != NO_POSITION && positions[1] != NO_POSITION) {
            return positions;
        } else {
            return null;
        }
    }
}
