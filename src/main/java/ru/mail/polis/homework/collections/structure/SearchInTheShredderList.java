package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
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
        return null;
    }
}
