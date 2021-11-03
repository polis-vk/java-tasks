package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
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

    private final static Map<Character, Character> map = Map.of(
            '(', ')',
            '[', ']',
            '{', '}',
            '<', '>'
    );

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }

        Deque<Character> stack = new LinkedList<>();
        char symbol;

        for (int i = 0; i < value.length(); i++) {
            symbol = value.charAt(i);
            if (map.get(symbol) != null) {
                stack.addLast(symbol);
            }

            if (map.containsValue(symbol)) {
                if (stack.isEmpty()) {
                    return false;
                }

                if (map.get(stack.pollLast()) == symbol) {
                    continue;
                }
                return false;
            }
        }
        return stack.isEmpty();
    }
}
