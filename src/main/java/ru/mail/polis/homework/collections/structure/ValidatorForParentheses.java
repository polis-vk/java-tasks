package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
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
    private final static Map<Character, Character> BRACKETS_TABLE = new HashMap<Character, Character>() {{
        put(']', '[');
        put(')', '(');
        put('>', '<');
        put('}', '{');
    }};

    public static boolean validate(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        Deque<Character> brackets = new ArrayDeque<>();
        int bracketCount = 0;
        for (char literal : value.toCharArray()) {
            if (BRACKETS_TABLE.containsValue(literal)) {
                brackets.add(literal);
                continue;
            }
            if (BRACKETS_TABLE.containsKey(literal)) {
                if (brackets.isEmpty() || brackets.pollLast() != BRACKETS_TABLE.get(literal)) {
                    return false;
                }
                bracketCount++;
            }
        }
        return brackets.isEmpty() && bracketCount != 0;
    }
}
