package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;

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
        if (value == null || value.equals("") || value.length() == 1) {
            return false;
        }
        ArrayList<Character> stack = new ArrayList<>();
        for (Character i : value.toCharArray()) {
            if (i.equals('[') || i.equals('{') || i.equals('<') || i.equals('(')) {
                stack.add(i);
            }
            if (i.equals(']') || i.equals('}') || i.equals('>') || i.equals(')')) {
                if (stack.size() == 0 || !stack.get(stack.size() - 1).equals(returnOpenBracket(i))) {
                    return false;
                }
                stack.remove(stack.size() - 1);
            }
        }
        return stack.isEmpty();
    }

    static private char returnOpenBracket(Character bracket) {
        switch (bracket) {
            case ']': {
                return '[';
            }
            case '}': {
                return '{';
            }
            case '>': {
                return '<';
            }
            case ')': {
                return '(';
            }
        }
        return 0;
    }
}