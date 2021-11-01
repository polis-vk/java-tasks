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

        int count = 0;
        int[] positions = new int[partStrings.size()];
        for (int i = 0; i < partStrings.size(); ++i) {
            if (value.contains(partStrings.get(i))) {
                positions[count++] = i;
            }
        }
        if (count > 1) {
            int[] realPositions = new int[count];
            int _count = 0;
            int begin = 0;
            boolean isNeededToExitFromCycle = false;
            for (int i = 0; i < count && !isNeededToExitFromCycle; ++i) {
                for (int j = 0; j < count && !isNeededToExitFromCycle; ++j) {
                    if (value.startsWith(partStrings.get(positions[j]), begin)) {
                        realPositions[_count++] = positions[j];
                        begin += partStrings.get(positions[j]).length();
                    }
                    if (begin >= value.length()) {
                        isNeededToExitFromCycle = true;
                    }
                }
            }
            return realPositions;
        }
        return null;
    }
}
