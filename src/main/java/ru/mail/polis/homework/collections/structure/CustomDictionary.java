package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    private final Set<String> words = new HashSet<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - []
     */
    public boolean add(String value) {
        return words.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - []
     */
    public boolean contains(String value) {
        return words.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - []
     */
    public boolean remove(String value) {
        return words.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - []
     */
    public List<String> getSimilarWords(String value) {
        List<String> similar = new ArrayList<>();
        char[] valueSorted = value.toLowerCase().toCharArray();
        Arrays.sort(valueSorted);
        for (String word : words) {
            char[] wordSorted = word.toLowerCase().toCharArray();
            Arrays.sort(wordSorted);
            if (consistOfSameLetters(valueSorted, wordSorted)) {
                similar.add(word);
            }
        }
        return similar;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - []
     */
    public int size() {
        return words.size();
    }

    private static boolean consistOfSameLetters(char[] a1, char[] a2) {
        int i = 0;
        int j = 0;
        while (i < a1.length || j < a2.length) {
            if ((i >= a1.length) || (j >= a2.length) || (a1[i] != a2[j])) {
                return false;
            }
            do {
                i++;
            } while ((i < a1.length) && (a1[i - 1] == a1[i]));
            do {
                j++;
            } while ((j < a2.length) && (a2[j - 1] == a2[j]));
        }
        return true;
    }

}
