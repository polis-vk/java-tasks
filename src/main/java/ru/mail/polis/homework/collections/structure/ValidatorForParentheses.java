package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

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

        Stack<Character> brackets = new Stack<>();
        boolean hasBrackets = false;

        for (int i = 0; i < value.length(); ++i) {
            switch (value.charAt(i)) {
                case '[':
                case '{':
                case '<':
                case '(': {
                    brackets.push(value.charAt(i));
                    hasBrackets = true;
                    break;
                }
                case ']': {
                    if (brackets.isEmpty() || brackets.pop() != '[') {
                        return false;
                    }
                    break;
                }
                case '}': {
                    if (brackets.isEmpty() || brackets.pop() != '{') {
                        return false;
                    }
                    break;
                }
                case '>': {
                    if (brackets.isEmpty() || brackets.pop() != '<') {
                        return false;
                    }
                    break;
                }
                case ')': {
                    if (brackets.isEmpty() || brackets.pop() != '(') {
                        return false;
                    }
                    break;
                }
            }
        }

        return hasBrackets && brackets.isEmpty();
    }
}
