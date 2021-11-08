package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
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
    private static final Map<Character, Character> openingBracketToClosing = new HashMap<>();

    static {
        openingBracketToClosing.put('(', ')');
        openingBracketToClosing.put('[', ']');
        openingBracketToClosing.put('{', '}');
        openingBracketToClosing.put('<', '>');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean hasBrackets = false;
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);
            if (isOpeningBracket(current)) {
                hasBrackets = true;
                stack.push(current);
            } else if (isCLosingBracket(current) && !checkClosingBracket(current, stack)) {
                return false;
            }
        }

        return hasBrackets && stack.isEmpty();
    }

    private static boolean isOpeningBracket(char c) {
        return openingBracketToClosing.containsKey(c);
    }

    private static boolean isCLosingBracket(char c) {
        return openingBracketToClosing.containsValue(c);
    }

    private static boolean checkClosingBracket(char c, Deque<Character> stack) {
        if (stack.isEmpty()) {
            return false;
        }
        char elem = stack.pop();
        return openingBracketToClosing.get(elem) == c;
    }
}
