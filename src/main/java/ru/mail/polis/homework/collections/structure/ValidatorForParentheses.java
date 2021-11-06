package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

    private static final Map<Character, Character> bracket = new HashMap<>();

    static {
        bracket.put(')', '(');
        bracket.put('}', '{');
        bracket.put('>', '<');
        bracket.put(']', '[');
    }

    public static boolean validate(String value) {
        if (value == null || value.equals("") || value.length() == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (Character i : value.toCharArray()) {
            if (bracket.containsValue(i)) {
                stack.push(i);
            } else if (bracket.containsKey(i) && (stack.size() == 0 || !bracket.get(i).equals(stack.pop()))) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}