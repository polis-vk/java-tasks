package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
        Deque<Character> stack = new ArrayDeque<>();
        boolean gotBrackets = false; // for strings without any brackets
        Map<Character, Character> pair = new HashMap<>();
        pair.put('(', ')');
        pair.put('[', ']');
        pair.put('{', '}');
        pair.put('<', '>');

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (pair.containsKey(c)) {
                stack.push(c);
                gotBrackets = true;
            } else if (!stack.isEmpty() && c == pair.get(stack.peek())) {
                stack.pop();
            }
        }
        return stack.isEmpty() && gotBrackets;
    }
}
