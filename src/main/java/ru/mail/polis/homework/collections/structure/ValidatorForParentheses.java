package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;

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
        Deque<Character> deque = new LinkedList<>();
        boolean wasParenthesis = false;
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '{':
                case '[':
                case '(':
                case '<':
                    wasParenthesis = true;
                    deque.push(c);
                    break;
                case '}':
                case ']':
                case ')':
                case '>':
                    if (deque.isEmpty() || deque.pop() != getFriendlyParenthesis(c)) {
                        return false;
                    }
                    break;
            }
        }
        return deque.isEmpty() && wasParenthesis;
    }

    private static char getFriendlyParenthesis(char parenthesis) {
        switch (parenthesis) {
            case '}':
                return '{';
            case ')':
                return '(';
            case ']':
                return '[';
            case '>':
                return '<';
        }
        return 0;
    }
}
