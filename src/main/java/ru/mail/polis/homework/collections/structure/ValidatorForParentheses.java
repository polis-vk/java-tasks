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
        bracket.put('(', ')');
        bracket.put('{', '}');
        bracket.put('<', '>');
        bracket.put('[', ']');
    }

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        boolean hasAnyBracket = false;
        for (char c : value.toCharArray()) {
            if (bracket.containsKey(c)) {
                stack.push(c);
                hasAnyBracket = true;
            } else if (!stack.isEmpty() && c == bracket.get(stack.peek())) {
                stack.pop();
            } else if (bracket.containsValue(c)) {
                return false;
            }
        }
        return stack.isEmpty() && hasAnyBracket;
    }
}