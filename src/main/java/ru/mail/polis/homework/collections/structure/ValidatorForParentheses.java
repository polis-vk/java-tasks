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
 *      "(-b + (x)^2)/(2+4)" - true
 *      "Понедельники меня угнетают ((" - false
 *
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {

    public static boolean validate(String value) {
        if (value == null || value.length() < 2) {
            return false;
        }

        boolean valid = false;
        Deque<Character> stack = new ArrayDeque<Character>();

        for (int i = 0; i < value.length(); ++i) {
            final char ch = value.charAt(i);
            switch (ch) {
                case '(':
                case '[':
                case '{':
                case '<': {
                    valid = true;
                    stack.push(ch);
                    continue;
                }
                case ')': {
                    if (!stack.isEmpty() && stack.pop() == '(') {
                        continue;
                    }
                    break;
                }
                case ']': {
                    if (!stack.isEmpty() && stack.pop() == '[') {
                        continue;
                    }
                    break;
                }
                case '}': {
                    if (!stack.isEmpty() && stack.pop() == '{') {
                        continue;
                    }
                    break;
                }
                case '>': {
                    if (!stack.isEmpty() && stack.pop() == '<') {
                        continue;
                    }
                    break;
                }
                default: {
                    continue;
                }
            }
            return false;
        }
        return valid && stack.isEmpty();
    }
}
