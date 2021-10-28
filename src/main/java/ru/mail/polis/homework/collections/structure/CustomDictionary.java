package ru.mail.polis.homework.collections.structure;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private final HashSet<String> dictionary;

    public CustomDictionary() {
        this.dictionary = new HashSet<>();
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - O(1)
     */
    public boolean add(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException();
        }
        return dictionary.add(value);
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - O(1) В среднем, в худшем O(logn), так как структура bucket = TreeMap
     */
    public boolean contains(String value) {
        return dictionary.contains(value);
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
        return dictionary.remove(value);
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
     * Сложность - O(i*j), где i - длина слова, а j - размер массива слов в dictionary
     */
    public List<String> getSimilarWords(String value) {
        final ArrayList<String> wordsToReturn = new ArrayList<>();
        for (String word : dictionary) {
            if (compareWords(word, value)) {
                if (word.length() != value.length()) {
                    continue;
                }
                wordsToReturn.add(word);
            }
        }
        return wordsToReturn;
    }

    private boolean compareWords(String word, String value) {
        ArrayList<Character> chars = new ArrayList<>();
        for (char character : word.toCharArray()) {
            chars.add(Character.toLowerCase(character));
        }
        for (char character : value.toCharArray()) {
            Character temp = Character.toLowerCase(character);
            chars.remove(temp);
        }
        return chars.isEmpty();
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - O(1)
     */
    public int size() {
        return dictionary.size();
    }
}
