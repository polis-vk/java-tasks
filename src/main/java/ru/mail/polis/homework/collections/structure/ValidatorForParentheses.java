package ru.mail.polis.homework.collections.structure;

import java.util.LinkedList;

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
        if (value == null || value.isEmpty()) {
            return false;
        }
        return realizeValidate(value);
    }

    private static boolean realizeValidate(String value) {
        LinkedList<Character> needToClose = new LinkedList<>();//last open bracket stack
        for (Character symbol : value.toCharArray()) {
            if (symbol.equals('(') || symbol.equals('[') || symbol.equals('{') || symbol.equals('<')) {
                needToClose.add(symbol);
            } else {
                if (symbol.equals(')') || symbol.equals(']') || symbol.equals('}') || symbol.equals('>')) {
                    if (needToClose.size() == 0 || !needToClose.pollLast().equals(getOpenBracket(symbol))) {
                        return false;
                    }
                }
            }
        }
        return needToClose.isEmpty();
    }

    private static Character getOpenBracket(Character bracket) {
        if (bracket.equals(')')) {
            return '(';
        }
        if (bracket.equals(']')) {
            return '[';
        }
        if (bracket.equals('>')) {
            return '<';
        }
        return '{';
    }
}