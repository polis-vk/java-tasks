package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean withBrackets = false;
        char[] bracketsArr = value.toCharArray();
        Deque<Character> deque = new LinkedList<>();

        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        map.put('>', '<');
        HashSet<Character> openBracketsSet = new HashSet<>(Arrays.asList('(', '[', '{', '<'));
        HashSet<Character> closeBracketsSet = new HashSet<>(Arrays.asList(')', ']', '}', '>'));

        for (char c : bracketsArr) {
            if (!(openBracketsSet.contains(c) || closeBracketsSet.contains(c))) {
                continue;
            }
            withBrackets = true;
            if (openBracketsSet.contains(c)) {
                deque.addLast(c);
            } else if (deque.isEmpty() || map.get(c) != deque.pollLast()) {
                return false;
            }
        }
        return deque.isEmpty() && withBrackets;
    }
}
