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

    private static final Map<Character, Character> INITIAL_TO_CLOSING_PARENTHESES;
    private static final Map<Character, Character> CLOSING_TO_INITIAL_PARENTHESES;

    static {
        INITIAL_TO_CLOSING_PARENTHESES = new HashMap<>();

        INITIAL_TO_CLOSING_PARENTHESES.put('{', '}');
        INITIAL_TO_CLOSING_PARENTHESES.put('[', ']');
        INITIAL_TO_CLOSING_PARENTHESES.put('<', '>');
        INITIAL_TO_CLOSING_PARENTHESES.put('(', ')');

        CLOSING_TO_INITIAL_PARENTHESES = new HashMap<>();

        INITIAL_TO_CLOSING_PARENTHESES.entrySet()
                .stream()
                .forEach(entry -> CLOSING_TO_INITIAL_PARENTHESES.put(entry.getValue(), entry.getKey()));
    }

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }
        Deque<Character> initialParentheses = new LinkedList<>();
        boolean hasAdded = false;
        for (int i = 0; i < value.length(); i++) {
            char symbol = value.charAt(i);
            if (INITIAL_TO_CLOSING_PARENTHESES.containsKey(symbol)) {
                initialParentheses.add(symbol);
                hasAdded = true;
                continue;
            }
            Character initialParenthesisForSymbol = CLOSING_TO_INITIAL_PARENTHESES.get(symbol);
            if (initialParenthesisForSymbol != null &&
                (initialParentheses.isEmpty() ||
                initialParentheses.pollLast() != initialParenthesisForSymbol)) {
                return false;
            }
        }
        return hasAdded && initialParentheses.isEmpty();
    }
}
