package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashSet<String> dict = new HashSet<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(1)
     */
    public boolean add(String value) {
        return dict.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(1) в среднем
     */
    public boolean contains(String value) {
        return dict.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - O(1)
     */
    public boolean remove(String value) {
        return dict.remove(value);
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из тех же букв что нам передали строку.
     * Примеры: сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - O(n * m), где n - кол-во слов в словаре, m - длина входного слова
     */
    public List<String> getSimilarWords(String value) {
        final ArrayList<String> similarWords = new ArrayList<>();
        for (String word : dict) {
            if (word.length() != value.length()) {
                continue;
            }
            final ArrayList<Character> wordCharList = new ArrayList<>();
            for (char letter : word.toCharArray()) {
                wordCharList.add(Character.toLowerCase(letter));
            }
            for (char letter : value.toCharArray()) {
                wordCharList.remove((Character) Character.toLowerCase(letter));
            }
            if (wordCharList.isEmpty()) {
                similarWords.add(word);
            }
        }
        return similarWords;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return dict.size();
    }

}
