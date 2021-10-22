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
        if (value == null || value.equals("")) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (char c : value.toCharArray()) {
            switch (c) {
                case '[':
                case '(':
                case '{':
                case '<': stack.push(c); break;
                case ']':
                case ')':
                case '}':
                case '>':
                    if (isPair(stack.peek(), c)) {
                        stack.pop();
                    }
                    else {
                        break;
                    }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isPair(char a, char b) {
        switch (a) {
            case '[': return b == ']';
            case '{': return b == '}';
            case '(': return b == ')';
            case '<': return b == '>';
        }
        return false;
    }
}
