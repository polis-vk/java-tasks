package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;

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

    public static boolean validate(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : value.toCharArray()) {
            if (!isBracket(ch)) {
                continue;
            }
            if (isOpening(ch)) {
                stack.push(ch);
                continue;
            }
            if (stack.isEmpty() || stack.pop() != pair(ch)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean isBracket(char ch) {
        return isClosing(ch) || isOpening(ch);
    }

    private static boolean isOpening(char bracket) {
        switch (bracket) {
            case '[':
            case '{':
            case '<':
            case '(':
                return true;
            default:
                return false;
        }
    }

    private static char pair(char bracket) {
        switch (bracket) {
            case ']':
                return '[';
            case '}':
                return '{';
            case '>':
                return '<';
            case ')':
                return '(';
            default:
                return '?';
        }
    }

    private static boolean isClosing(char bracket) {
        switch (bracket) {
            case ']':
            case '}':
            case '>':
            case ')':
                return true;
            default:
                return false;
        }
    }
}
