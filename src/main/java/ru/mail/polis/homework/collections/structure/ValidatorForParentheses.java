package ru.mail.polis.homework.collections.structure;

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

    private static final Map<Character, Character> parenthesis =
            Map.of('[', ']',
                    '{', '}',
                    '<', '>',
                    '(', ')');

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean hasParenthesis = false;
        Stack<Character> stack = new Stack<>();
        char[] valueCharArray = value.toCharArray();
        for (char c : valueCharArray) {
            if (parenthesis.containsKey(c)) {
                stack.push(parenthesis.get(c));
                hasParenthesis = true;
            }
            if (parenthesis.containsValue(c)) {
                hasParenthesis = true;
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty() && hasParenthesis;
    }
}