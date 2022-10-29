package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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
    private static final Set<Character> openedBraces = new HashSet<>(Arrays.asList('(', '[', '{', '<'));
    private static final Map<Character, Character> mapBraces = new HashMap<>();

    static {
        mapBraces.put(')', '(');
        mapBraces.put(']', '[');
        mapBraces.put('}', '{');
        mapBraces.put('>', '<');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        boolean thereWasBracket = false;
        char currentChar;
        for (int index = 0; index < value.length(); index++) {
            currentChar = value.charAt(index);
            if (openedBraces.contains(currentChar)) {
                thereWasBracket = true;
                stack.push(currentChar);
            } else {
                if (!stack.isEmpty() && stack.peek() == mapBraces.get(currentChar)) {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty() && thereWasBracket;
    }
}
