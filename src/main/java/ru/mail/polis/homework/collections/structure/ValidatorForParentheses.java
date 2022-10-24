package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
        Deque<Character> stack = new ArrayDeque<>();
        if (value == null || value.length() == 0) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char checkValueChar = value.charAt(i);
            if (checkValueChar == '(' || checkValueChar == '[' || checkValueChar == '{' || checkValueChar == '<') {
                stack.push(checkValueChar);
                continue;
            }
            if (stack.isEmpty() && i == value.length() - 1) {
                return false;
            }
            if (!stack.isEmpty()) {
                char bracket;
                switch (checkValueChar) {
                    case ')':
                        bracket = stack.pop();
                        if (bracket == '{' || bracket == '[' || bracket == '<') {
                            return false;
                        }
                        break;

                    case '}':
                        bracket = stack.pop();
                        if (bracket == '(' || bracket == '[' || bracket == '<') {
                            return false;
                        }
                        break;

                    case ']':
                        bracket = stack.pop();
                        if (bracket == '(' || bracket == '{' || bracket == '<') {
                            return false;
                        }
                        break;
                    case '>':
                        bracket = stack.pop();
                        if (bracket == '(' || bracket == '{' || bracket == '[') {
                            return false;
                        }
                        break;
                }
            }
        }
        return (stack.isEmpty());
    }
}

