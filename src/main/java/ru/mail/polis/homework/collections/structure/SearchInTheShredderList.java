package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
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
        int middle = value.length() / 2;
        String firstPart = value.substring(0, middle);
        String secondPart = value.substring(middle);
        int[] ans = new int[]{-1, -1};
        for (int i = 0; i < partStrings.size(); i++) {
            String s = partStrings.get(i);
            if (firstPart.equals(s)) {
                ans[0] = i;
            } else if (secondPart.equals(s)) {
                ans[1] = i;
            }
            if (ans[0] != -1 && ans[1] != -1) {
                break;
            }
        }
        if (ans[0] == -1 || ans[1] == -1) {
            return null;
        }
        return ans;
    }
}
