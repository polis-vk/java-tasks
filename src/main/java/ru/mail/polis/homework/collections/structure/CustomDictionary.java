package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final Set<String> words = new HashSet<String>();

    /**
     * Сохранить строку в структуру данных
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     *
     * Сложность - [O(n)]
     */
    public boolean add(String value) {
        checkForValueValidity(value);
        return words.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     *
     * Сложность - [O(n)]
     */
    public boolean contains(String value) {
        checkForValueValidity(value);
        return words.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     *
     * Сложность - [O(n)]
     */
    public boolean remove(String value) {
        checkForValueValidity(value);
        return words.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     *
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     *
     * Сложность - [O(k)(составление главной карты)
     *   + n*(O(k) + O(m))(для каждого из n слов создаём карту и сверяемся с главной картой)
     *   |-> O(max(k, m)*n), где k - среднее кол-во символов в строке, а m - средний размер карты
     *   (не можем гарантировать, что k,m < n, и уж тем более что k,m << n]
     */
    public List<String> getSimilarWords(String value) {
        checkForValueValidity(value);
        HashMap<String, Integer> valueMap = createTableOfFrequency(value);

        // Для каждого слова составляем специальную табличку
        // и сопоставляем с уже созданной табличкой valueMap
        List<String> matched = new ArrayList<>();
        for (String curWord: words) {
            HashMap<String, Integer> curMap = createTableOfFrequency(curWord);
            if (areEqualTables(valueMap, curMap)) {
                matched.add(curWord);
            }
        }
        return matched;
    }

    // Сопоставляем псевдо-регулярное выражение [tT] с частотой его встречаемости
    // Сложность - [2*k |-> O(k), где k - кол-во символов в строке]
    private HashMap<String /*regexForLetter*/, Integer /*frequency*/> createTableOfFrequency(String s) {
        HashMap<String, Integer> sMap = new HashMap<String, Integer>();
        for (int i = 0; i < s.length(); ++i) {
            String curCh = s.substring(i, i + 1);
            String curRegex = curCh.toLowerCase(Locale.ROOT) + curCh.toUpperCase(Locale.ROOT);
            sMap.put(curRegex, sMap.getOrDefault(curRegex, 0) + 1);
        }
        return sMap;
    }

    // Сложность - O(1)|O(m), где m = lhs.size()
    private boolean areEqualTables(HashMap<String, Integer> lhs, HashMap<String, Integer> rhs) {
        boolean isMatched = true;
        if (lhs.size() != rhs.size()) {
            isMatched = false;
        } else {
            for (Map.Entry<String, Integer> mainPair: lhs.entrySet()) {
                isMatched = rhs.get(mainPair.getKey()).equals(mainPair.getValue());
                if (!isMatched) {
                    break;
                }
            }
        }
        return isMatched;
    }

    /**
     * Колл-во хранимых строк.
     * @return - Колл-во хранимых строк.
     *
     * Сложность - [O(1)]
     */
    public int size() {
        return words.size();
    }

    private static void checkForValueValidity(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Illegal value");
        }
    }

}
