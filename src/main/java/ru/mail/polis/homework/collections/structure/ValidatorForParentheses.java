package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    private static final Set<Character> leftBrackets = new HashSet<>(Arrays.asList('[', '{', '<', '('));
    private static final Set<Character> rightBrackets = new HashSet<>(Arrays.asList(']', '}', '>', ')'));

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        boolean hasNoBrackets = true;
        for (Character c : value.toCharArray()) {
            if (leftBrackets.contains(c)) {
                stack.push(c);
                hasNoBrackets = false;
            } else if (rightBrackets.contains(c)) {
                if (stack.empty() || !stack.peek().equals(getLeftBracket(c))) {
                    return false;
                }
                hasNoBrackets = false;
                stack.pop();
            }
        }
        return stack.empty() && !hasNoBrackets;
    }

    private static char getLeftBracket(char c) {
        switch (c) {
            case ']':
                return '[';
            case '}':
                return '{';
            case '>':
                return '<';
            case ')':
                return '(';
        }
        return ' ';
    }
}
