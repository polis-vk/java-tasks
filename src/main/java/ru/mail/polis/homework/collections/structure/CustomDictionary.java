package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Задание оценивается в 4 тугрика.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    // Создаем словарь, ключем которого является словарь, где ключами являются буквы, а значением - сколько раз эта
    // буква содержится в слове(словари без дубликатирования). И размер.
    private Map<String, Map<Character, Integer>> dictionary = new HashMap<>();
    private int size = 0;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - []
     */

    // Солжность O(n). Где n - количество элементов в строке. put ~ O(n).
    public boolean add(String value) {

        if (contains(value)) {
            return false;
        }
        Map<Character, Integer> characterMap = createCharacterMap(value);
        dictionary.put(value, characterMap);
        size = dictionary.size();
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - []
     */
    // Сложность (O(1)~O(m)), где m - кол-во элементов в словаре. n - кол-во элементов в строке.
    public boolean contains(String value) {
        return dictionary.containsKey(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - []
     */

    // Сложность O(n). Есть get - O(n).
    public boolean remove(String value) {
        if (dictionary.remove(value, dictionary.get(value))) {
            size = dictionary.size();
            return true;
        }
        return false;
    }

    /**
     * Возвращает список из сохраненных ранее строк, которые состоят
     * из того же набора букв, что нам передали строку.
     * Примеры:
     * сохраняем строки ["aaa", "aBa", "baa", "aaB"]
     * При поиске по строке "AAb" нам должен вернуться следующий
     * список: ["aBa","baa","aaB"]
     * <p>
     * сохраняем строки ["aaa", "aAa", "a"]
     * поиск "aaaa"
     * результат: []
     * Как можно заметить - регистр строки не должен влиять на поиск, при этом
     * возвращаемые строки хранятся в том виде, что нам передали изначально.
     *
     * @return - список слов которые состоят из тех же букв, что и передаваемая
     * строка.
     * <p>
     * Сложность - []
     */
    // Сложность в лучшем случае O(m). Худший O(nm), где n - количество элементов в словаре. m - количество элементов
    // строки.
    public List<String> getSimilarWords(String value) {
        LinkedList<String> stringLinkedList = new LinkedList<>();
        Map<Character, Integer> characterMap = createCharacterMap(value);
        for (String obj : dictionary.keySet()) {
            if (dictionary.get(obj).equals(characterMap)) {
                stringLinkedList.add(obj);
            }
        }
        return stringLinkedList;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - []
     */
    // Сложность О(1).
    public int size() {
        return size;
    }

    // Сложность O(n).
    private Map<Character, Integer> createCharacterMap(String value) {

        // Создаем представление каждого слова в словаре.
        // Если строка пустая или null, то выкинем исключение.
        if (value == null || value.length() == 0) {
            throw new IllegalArgumentException();
        }

        // Создаем словарь.
        Map<Character, Integer> characterMap = new HashMap<>();

        // Приводим слово в нижний регистр (от регистра не зависит задача). Создаем представление слова в словаре и
        // записываем кол-во таких букв в слове.
        String lowerCase = value.toLowerCase();
        for (char obj : lowerCase.toCharArray()) {
            characterMap.merge(obj, 1, Integer::sum);
        }
        return characterMap;
    }
}
