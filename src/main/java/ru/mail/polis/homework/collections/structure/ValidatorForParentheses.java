package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
    private static final Map<Character, Character> BRACKETS = new HashMap<>();

    static {
        BRACKETS.put(')', '(');
        BRACKETS.put(']', '[');
        BRACKETS.put('}', '{');
        BRACKETS.put('>', '<');
    }

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }
        boolean wasBracket = false;
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : value.toCharArray()) {
            if (!BRACKETS.containsKey(ch) && !BRACKETS.containsValue(ch)) {
                continue;
            }
            wasBracket = true;
            if (BRACKETS.containsValue(ch)) {
                stack.push(ch);
                continue;
            }
            if (stack.isEmpty() || stack.pop() != BRACKETS.get(ch)) {
                return false;
            }
        }
        return stack.isEmpty() && wasBracket;
    }

}
