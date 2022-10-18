package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Задание оценивается в 2 тугрика.
 * Одна из самых популярных задач.
 * Реализовать метод, который проверяет правильность написания скобок в строке.
 * В строке помимо скобок могут содержаться и другие символы.
 * Скобки могут быть: [],{},<>,()
 * Примеры:
 *      "(-b + (x)^2)/(2+4)" - true
 *      "Понедельники меня угнетают ((" - false
 *
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {

    public static Map<Character, Character> getMap() {
        Map<Character, Character> map = new HashMap<>();
        map.put(']', '[');
        map.put(')', '(');
        map.put('}', '{');
        map.put('>', '<');
        return map;
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        final Map<Character, Character> map = getMap();

        Deque<Character> deque = new LinkedList<>();
        boolean hasParentheses = false;

        for (char c : value.toCharArray()) {
            switch (c) {
                case '(':
                case '{':
                case '[':
                case '<':
                    hasParentheses = true;
                    deque.addLast(c);
                    break;
                case ')':
                case '}':
                case ']':
                case '>':
                    if (deque.isEmpty() || deque.getLast() != map.get(c)) {
                        return false;
                    }
                    deque.removeLast();
                    break;
            }
        }

        return deque.isEmpty() && hasParentheses;
    }
}
