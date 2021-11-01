package ru.mail.polis.homework.collections.structure;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {

    private HashSet<String> data;

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [Average - O(1), Worst - O(n)]
     */
    public boolean add(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        data.add(value);
        return true;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [Average - O(1), Worst - O(n)]
     */
    public boolean contains(String value) {
        return data.contains(value);
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [Average - O(1), Worst - O(n)]
     */
    public boolean remove(String value) {
        return data.remove(value);
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
     * Сложность - [O(n)]
     */
    public List<String> getSimilarWords(String value) {
       LinkedList<String> listOfWords = new LinkedList<String>();
       for(String element : data){
        if(isSimilar(element, value)){
            listOfWords.add(element);
        }
       }
       return listOfWords;
    }

    private static boolean isSimilar(String first, String second){
        TreeSet<Character> firstSet = new TreeSet<Character>();
        TreeSet<Character> secondSet = new TreeSet<Character>();
        for(Character el : first.toCharArray()){
            firstSet.add(el);
        }
        for(Character el : second.toCharArray()){
            secondSet.add(el);
        }
        return firstSet.equals(secondSet);
    }
    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return data.size();
    }
}
