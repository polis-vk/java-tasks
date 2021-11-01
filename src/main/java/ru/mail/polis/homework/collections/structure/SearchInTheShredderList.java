package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
     * —ложность - [n + k^2 => O(n) амортизированно (исключение: k = n),
     * k - количество найденных подслов слова value среди ShredderList.size() значений]
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
