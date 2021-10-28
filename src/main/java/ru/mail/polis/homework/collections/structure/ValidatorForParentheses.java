package ru.mail.polis.homework.collections.structure;

import java.util.LinkedList;

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
    private static final char[] openingBrackets = new char[]{'[', '{', '<', '('};
    private static final char[] closingBrackets = new char[]{']', '}', '>', ')'};

    private static boolean isOpening(char character) {
        for (char openingBracket : openingBrackets) {
            if (openingBracket == character) {
                return true;
            }
        }
        return false;
    }

    private static int getClosing(char character) {
        for(int i = 0; i < closingBrackets.length; i++) {
            if(closingBrackets[i] == character) {
                return i;
            }
        }
        return -1;
    }

    public static boolean validate(String value) {
        if(value == null || value.isEmpty()) {
            return false;
        }
        LinkedList<Character> brackets = new LinkedList<>();
        for(int i = 0; i < value.length(); i++) {
            if(isOpening(value.charAt(i))) {
                brackets.addLast(value.charAt(i));
            } else if(getClosing(value.charAt(i)) != -1) {
                if(brackets.isEmpty() || brackets.getLast() != openingBrackets[getClosing(value.charAt(i))]) {
                    return false;
                }
                brackets.removeLast();
            }
        }
        return brackets.isEmpty();
    }
}
