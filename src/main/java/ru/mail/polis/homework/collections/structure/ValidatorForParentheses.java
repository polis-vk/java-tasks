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
    private static final Set<Character> OPEN_BRACKETS = new HashSet<>(Arrays.asList('(', '[', '{', '<'));
    private static final Set<Character> CLOSE_BRACKETS = new HashSet<>(Arrays.asList(')', ']', '}', '>'));

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }
        boolean wasBracket = false;
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : value.toCharArray()) {
            if (!OPEN_BRACKETS.contains(ch) && !CLOSE_BRACKETS.contains(ch)) {
                continue;
            }
            wasBracket = true;
            if (OPEN_BRACKETS.contains(ch)) {
                stack.push(ch);
                continue;
            }
            if (stack.isEmpty() || stack.pop() != getOpenBracket(ch)) {
                return false;
            }
        }
        return stack.isEmpty() && wasBracket;
    }

    private static Character getOpenBracket(Character bracket) {
        if (bracket.equals(')')) {
            return '(';
        }
        if (bracket.equals(']')) {
            return '[';
        }
        if (bracket.equals('>')) {
            return '<';
        }
        return '{';
    }

}