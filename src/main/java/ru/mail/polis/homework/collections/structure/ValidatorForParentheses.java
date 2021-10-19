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
        if (value == null || value.isEmpty()) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < value.length(); i++) {
            switch (value.charAt(i)) {
                case '(':
                case '[':
                case '{':
                case '<':
                    stack.add(value.charAt(i));
                    break;
                case ')':
                    if (stack.isEmpty() || stack.removeLast() != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (stack.isEmpty() || stack.removeLast() != '[') {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.isEmpty() || stack.removeLast() != '{') {
                        return false;
                    }
                    break;
                case '>':
                    if (stack.isEmpty() || stack.removeLast() != '<') {
                        return false;
                    }
                    break;
            }
        }
        return stack.isEmpty();
    }
}
