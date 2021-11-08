package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
    private static final Map<Character, Character> openingBrackets = Map.ofEntries(Map.entry('[', ']'),
            Map.entry('(', ')'), Map.entry('{', '}'), Map.entry('<', '>'));
    private static final Set<Character> closingBrackets = Set.of(']', ')', '}', '>');

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean wereBracketsPresent = false;
        Deque<Character> currBrackets = new LinkedList<>();
        for (char character : value.toCharArray()) {
            if (openingBrackets.containsKey(character)) {
                wereBracketsPresent = true;
                currBrackets.addLast(character);
            } else if (closingBrackets.contains(character)) {
                if (currBrackets.isEmpty() || openingBrackets.get(currBrackets.getLast()) != character) {
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
