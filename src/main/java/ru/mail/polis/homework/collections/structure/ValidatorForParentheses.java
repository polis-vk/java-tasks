package ru.mail.polis.homework.collections.structure;

import java.util.Map;
import java.util.Set;
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
    private static final Map<Character, Character> brackets = Map.ofEntries(Map.entry('[', ']'),
            Map.entry('{', '}'), Map.entry('<', '>'), Map.entry('(', ')'));
    private static final Set<Character> closeBrackets = Set.of(']', '}', '>', ')');

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        boolean containsBrackets = false;
        Stack<Character> stack = new Stack<>();
        for (char c : value.toCharArray()) {
            if (brackets.containsKey(c)) {
                containsBrackets = true;
                stack.push(c);
            }
            else if (closeBrackets.contains(c)) {
                containsBrackets = true;
                if (stack.isEmpty()) {
                    return false;
                }
                else if (brackets.get(stack.peek()) == c) {
                    stack.pop();
                }
            }
        }
        return containsBrackets && stack.isEmpty();
    }
}
