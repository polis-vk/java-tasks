package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Задание оценивается в 2 балла.
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

    public static final String OPEN_BRACKETS = "([{<";
    public static final String CLOSING_BRACKETS = ")]}>";

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (char token : value.toCharArray()) {
            if (OPEN_BRACKETS.indexOf(token) != -1) {
                stack.push(token);
                continue;
            }

            final int index = CLOSING_BRACKETS.indexOf(token);
            if (index != -1 && (stack.isEmpty() || index != OPEN_BRACKETS.indexOf(stack.pop()))) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
