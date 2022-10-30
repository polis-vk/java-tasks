package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 тугрика.
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
        boolean flag = false;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            if (isOpeningBracket(character)) {
                flag = true;
                stack.push(character);
            } else if (isClosingBracket(character)) {
                if (stack.size() == 0 || stack.pop() != getOpeningBracket(character)) {
                    return false;
                }
            }
        }
        return flag && stack.size() == 0;
    }

    public static boolean isOpeningBracket(char character) {
        return character == '[' || character == '{' || character == '<' || character == '(';
    }

    public static boolean isClosingBracket(char character) {
        return character == ']' || character == '}' || character == '>' || character == ')';
    }

    public static char getOpeningBracket(char closingBracket) {
        switch (closingBracket) {
            case ']':
                return '[';
            case '}':
                return '{';
            case '>':
                return '<';
            case ')':
                return '(';
        }
        return '0';
    }
}


