package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;

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
        ArrayList<Character> stack = new ArrayList<>();
        for (Character c : value.toCharArray()) {
            switch (c) {
                case '[':
                case '{':
                case '<':
                case '(':
                    stack.add(c);
                    break;
                case ']':
                case '}':
                case '>':
                case ')':
                    int index = stack.lastIndexOf(getLeftBracket(c));
                    if (index == -1 || index != stack.size() - 1) {
                        return false;
                    }
                    stack.remove(index);
                    break;
            }
        }
        return stack.isEmpty();
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
