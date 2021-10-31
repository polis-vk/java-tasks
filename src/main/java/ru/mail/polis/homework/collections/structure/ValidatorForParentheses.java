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

    private static final char[] openBrackets = new char[]{'(', '[', '{', '<'};
    private static final char[] closeBrackets = new char[]{')', ']', '}', '>'};

    private static boolean isOpenBracket(char bracket) {
        for (char openBracket : openBrackets) {
            if (openBracket == bracket) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCloseBracket(char bracket) {
        for (char closeBracket : closeBrackets) {
            if (closeBracket == bracket) {
                return true;
            }
        }
        return false;
    }

    private static Character getOpenBracket(char closeBracket) {
        for (int i = 0; i < closeBrackets.length; i++) {
            if (closeBrackets[i] == closeBracket) {
                return openBrackets[i];
            }
        }
        return null;
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean check = false;
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < value.length(); i++) {
            if (isOpenBracket(value.charAt(i))) {
                check = true;
                stack.add(value.charAt(i));
            } else if (isCloseBracket(value.charAt(i))) {
                if (stack.isEmpty() || !stack.removeLast().equals(getOpenBracket(value.charAt(i)))) {
                    return false;
                }
            }
        }
        return check && stack.isEmpty();
    }
}
