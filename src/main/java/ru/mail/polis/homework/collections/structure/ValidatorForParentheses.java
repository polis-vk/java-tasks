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
 * "(-b + (x)^2)/(2+4)" - true
 * "Понедельники меня угнетают ((" - false
 * <p>
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Map<Character, Character> matcher = new HashMap<>();
        matcher.put(')', '(');
        matcher.put(']', '[');
        matcher.put('}', '{');
        matcher.put('>', '<');

        Deque<Character> closedBrackets = new LinkedList<>();
        boolean containsBracket = false;

        for (char symbol : value.toCharArray()) {
            if (matcher.containsValue(symbol)) {
                closedBrackets.push(symbol);
                containsBracket = true;
                continue;
            }

            if (matcher.containsKey(symbol) && (closedBrackets.isEmpty() || !closedBrackets.pop().equals(matcher.get(symbol)))) {
                return false;
            }
        }

        return containsBracket && closedBrackets.isEmpty();
    }
}