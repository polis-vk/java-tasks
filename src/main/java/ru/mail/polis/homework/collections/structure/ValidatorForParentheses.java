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
    private static final Map<Character, Character> brackets = Map.of('(', ')', '[', ']', '{', '}');

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (char c : value.toCharArray()) {
            if (brackets.containsKey(c)) {
                stack.push(c);
            } else if (!stack.isEmpty() && c == brackets.get(stack.peek())) {
                stack.pop();
            } else if (brackets.containsValue(c)) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
