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
