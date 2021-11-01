package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * «адание оцениваетс€ в 2 балла.
 * ќдна из самых попул€рных задач.
 * Ќаша структура хранит обрывки слов. Ќадо реализовать метод positionPartString
 * который вернет: позиции где наход€тс€ подстроки, из которых можно составить
 * переданное слово. “ак же известно что слова, которые писались в структуру, изначально
 * делились пополам дл€ записи в нее.
 * ќтрабатывать метод должен за ќ(n).
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
     * »щем позиции подстрок из которых можно составить передаваемое слово
     *
     * @param value - передаваемоей слово
     * @return - либо массив с реальными позици€ми подстрок если нашли, либо - null
     */
    public int[] positionPartString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        int[] positions = new int[N_PART_STRINGS];
        Arrays.fill(positions, NO_POSITION);
        int count = 0;
        // дл€ f(k) подстрок [left, right) ищем совпадени€
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
