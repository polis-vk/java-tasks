package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static int N_PART_STRINGS = 28;
    private static int NO_POSITION = -1;

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
        if (value == null || value.isEmpty()) {
            return null;
        }

        int[] positions = new int[N_PART_STRINGS];
        Arrays.fill(positions, NO_POSITION);
        int count = 0;
        // ��� f(k) �������� [left, right) ���� ����������
        int left = 0, right = 1;
        while (right <= value.length()) {
            for (int i = 0; i < N_PART_STRINGS; ++i) {
                int substringIndex = partStrings.indexOf(value.substring(left, right));
                if (substringIndex != -1) {
                    left = right;
                    positions[count++] = substringIndex;
                }
                ++right;
                if (right > value.length()) {
                    break;
                }
            }
        }
        if (count > 1) {
            int[] realPositions = new int[count];
            System.arraycopy(positions, 0, realPositions, 0, count);
            return realPositions;
        }
        return null;
    }
}
