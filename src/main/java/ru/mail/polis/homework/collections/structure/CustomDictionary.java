package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 * <p>
 * Сложнось не зависит от количества строк в структуре и у всех реализованных методов составляет O(1)
 * Тем не менее метод getCharacterSet, который преобразует строку к множеству символов линейно зависит от длины строки.
 * Сооветственно также от длины строки зависят и вызывающие его методы: add, remove, getSimilarWords.
 * <p>
 * Класс хранит две копии ссылок на добавленные строки. Расходуемая память зависит от количесвтва строк в структуре,
 * как O(n).
 */

public class CustomDictionary extends HashSet<String> {

    private Map<Set<Character>, List<String>> similarWords = new HashMap<>();

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(m)], где m - длина строки
     */
    public boolean add(String value) {
        boolean isInsertSucceed = super.add(value);
        if (isInsertSucceed) {
            addWordToSimilar(value);
        }
        return isInsertSucceed;
    }

    private Set<Character> getCharacterSet(String value) {
        HashSet<Character> characterValueRepresentation = new HashSet<>();
        for (Character c : value.toLowerCase().toCharArray()) {
            characterValueRepresentation.add(c);
        }

        return characterValueRepresentation;
    }

    private void addWordToSimilar(String value) {
        Set<Character> characterValueRepresentation = getCharacterSet(value);
        if (similarWords.containsKey(characterValueRepresentation)) {
            similarWords.get(characterValueRepresentation).add(value);
        } else {
            similarWords.put(characterValueRepresentation, Arrays.asList(value));
        }
    }

    private void removeWordFromSimilar(String value) {
        Set<Character> characterValueRepresentation = getCharacterSet(value);
        assert similarWords.containsKey(characterValueRepresentation);
        boolean removeResult = similarWords.get(characterValueRepresentation).remove(value);
        assert removeResult;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(1)]
     */
    public boolean contains(String value) {
        return super.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(m) + O(k)], где m - длина строки, а k - кол-во строк с таким же набором символов.
     * O(k) возникло из-за удаления элемента из списка по значению
     */
    public boolean remove(String value) {
        boolean isRemoveSucceed = super.remove(value);
        if (isRemoveSucceed) {
            removeWordFromSimilar(value);
        }
        return isRemoveSucceed;
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
     * <p>
     * Сложность - [O(m)], где m - длина строки
     */
    public List<String> getSimilarWords(String value) {
        Set<Character> characterValueRepresentation = getCharacterSet(value);
        return similarWords.getOrDefault(characterValueRepresentation, new ArrayList<>());
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return super.size();
    }


}
