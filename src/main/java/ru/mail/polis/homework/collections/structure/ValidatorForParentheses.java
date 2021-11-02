package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Задание оценивается в 2 балла.
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
    private static final Map<Character, Character> brackets = Map.ofEntries(Map.entry('[', ']'),
            Map.entry('(', ')'), Map.entry('{', '}'), Map.entry('<', '>'));

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean wereBracketsPresent = false;
        Deque<Character> currBrackets = new LinkedList<>();
        for (char character : value.toCharArray()) {
            if (brackets.containsKey(character)) {
                wereBracketsPresent = true;
                currBrackets.addLast(character);
            } else if (brackets.containsValue(character)) {
                if (currBrackets.isEmpty() || brackets.get(currBrackets.getLast()) != character) {
                    return false;
                }
                currBrackets.removeLast();
            }
        }
        if (!wereBracketsPresent) {
            return false;
        }
        return currBrackets.isEmpty();
    }
}
