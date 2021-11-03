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
    private static final Map<Character, Character> bracketsMap = Map.of('[', ']', '{', '}',
            '<', '>', '(', ')');

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        boolean hasNoBrackets = true;
        for (Character c : value.toCharArray()) {
            if (bracketsMap.containsKey(c)) {
                stack.push(c);
                hasNoBrackets = false;
            } else if (bracketsMap.containsValue(c)) {
                if (stack.empty() || c != bracketsMap.get(stack.peek())) {
                    return false;
                }
                hasNoBrackets = false;
                stack.pop();
            }
        }
        return stack.empty() && !hasNoBrackets;
    }
}
