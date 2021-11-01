package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Задание оценивается в 4 балла.
 * Необходимо реализовать класс которы умеет хранить строки и возвращать
 * список строк состоящий из того же набора буков, что ему передали строку.
 * Напишите какая сложность операций у вас получилась для каждого метода.
 */
public class CustomDictionary {
    HashMap<Character, Integer> hm = new HashMap<>();

    private static class Node {
        private final Map<Character, Node> children = new LinkedHashMap<>();
        private final Node parent;

        private Node(Node parent) {
            this.parent = parent;
        }

        public Map<Character, Node> getChildren() {
            return children;
        }

        public Node getParent() {
            return parent;
        }
    }

    private final Node root = new Node(null);

    private int size = 0;

    private String withZero(String value) {
        return value + '0';
    }


    public Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    /**
     * Сохранить строку в структуру данных
     *
     * @param value - передаваемая строка
     * @return - успешно сохранили строку или нет.
     * <p>
     * Сложность - [O(log(m))]
     */
    public boolean add(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Node current = root;
        boolean modified = false;
        for (char letter : withZero(value).toCharArray()) {
            Node existing = current.getChildren().get(letter);
            if (existing != null) {
                current = existing;
            } else {
                Node newNode = new Node(current);
                current.getChildren().put(letter, newNode);
                current = newNode;
                modified = true;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    /**
     * Проверяем, хранится ли такая строка уже у нас
     *
     * @param value - передаваемая строка
     * @return - есть такая строка или нет в нашей структуре
     * <p>
     * Сложность - [O(log(n))]
     */
    public boolean contains(String value) {
        return findNode(withZero(value)) != null;
    }

    /**
     * Удаляем сохраненную строку если она есть
     *
     * @param value - какую строку мы хотим удалить
     * @return - true если удалили, false - если такой строки нет
     * <p>
     * Сложность - [O(log(n))]
     */
    public boolean remove(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String sWithZero = withZero(value);
        Node current = findNode(sWithZero);
        if (current == null) {
            return false;
        }
        int i = sWithZero.length() - 1;
        while (current.getParent() != null) {
            if (!current.getChildren().isEmpty()) {
                break;
            }
            current.getParent().getChildren().remove(sWithZero.charAt(i));
            i--;
            current = current.getParent();
        }
        size--;
        return true;
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
     * Сложность - [O(nlog(n))]
     */

    public List<String> getSimilarWords(String value) {
        for (char letter : value.toLowerCase(Locale.ROOT).toCharArray()) {
            hm.computeIfPresent(letter, (k, v) -> v + 1);
            hm.putIfAbsent(letter, 1);
        }
        List<String> result = depthSearch(root, hm, new StringBuilder(), new ArrayList<>());
        hm.clear();
        return result;
    }

    private List<String> depthSearch(Node current, HashMap<Character, Integer> localHm, StringBuilder word, List<String> result) {
        for (Map.Entry<Character, Node> entry : current.getChildren().entrySet()) {
            if (entry.getKey() == '0') {
                boolean trigger = localHm.values().stream().mapToInt(value -> value).anyMatch(value -> value != 0);
                if (!trigger) {
                    result.add(word.toString());
                    return result;
                }
            }
            char lowerLetter = Character.toLowerCase(entry.getKey());
            if (localHm.containsKey(lowerLetter) && localHm.get(lowerLetter) != 0) {
                word.append(entry.getKey());

                localHm.compute(lowerLetter, (k, v) -> v - 1);
                depthSearch(entry.getValue(), localHm, word, result);

                localHm.compute(lowerLetter, (k, v) -> v + 1);

                word.deleteCharAt(word.length() - 1);
            }
        }
        return result;
    }

    /**
     * Колл-во хранимых строк.
     *
     * @return - Колл-во хранимых строк.
     * <p>
     * Сложность - [O(1)]
     */
    public int size() {
        return size;
    }


}
