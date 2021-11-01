package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
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

    private static final char[] openBrackets = new char[]{'(', '{', '[', '<'};
    private static final char[] closeBrackets = new char[]{')', '}', ']', '>'};

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return realizeValidate(value);
    }

    private static boolean realizeValidate(String value) {
        //LinkedList<Character> needToClose = new LinkedList<>();//last open bracket stack
        Deque<Character> needToClose = new LinkedList<>();
        boolean bracketChecker = false;
        for (Character symbol : value.toCharArray()) {
            if (isOpenBracket(symbol)) {
                needToClose.add(symbol);
                bracketChecker = true;
            } else {
                if (isCloseBracket(symbol)) {
                    if (needToClose.size() == 0 || !needToClose.pollLast().equals(getOpenBracket(symbol))) {
                        return false;
                    }
                }
            }
        }
        return needToClose.isEmpty() && bracketChecker;
    }

    private static boolean isOpenBracket(Character c) {
        for (Character element : openBrackets) {
            if (element.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCloseBracket(Character c) {
        for (Character element : closeBrackets) {
            if (element.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private static Character getOpenBracket(Character bracket) {
        for (int i = 0; i < closeBrackets.length; i++) {
            if (closeBrackets[i] == bracket) {
                return openBrackets[i];
            }
        }
        throw new IllegalArgumentException("Input: '" + bracket + "', but it should be one of brackets");
    }
}