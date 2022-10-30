package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;

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
        if (value == null || value.length() == 0) {
            return false;
        }
        String bracketSequence = value.replaceAll("[\\w\\s-+*/^]", "");
        Deque<Character> openingBrackets = new ArrayDeque<>();
        for (int i = 0; i < bracketSequence.length(); i++) {
            char bracket = bracketSequence.charAt(i);
            if (bracket == '[' || bracket == '{'
                    || bracket == '<' || bracket == '(') {
                openingBrackets.push(bracket);
                continue;
            }
            if (openingBrackets.isEmpty()) {
                return false;
            }
            if (!isSimilarBrackets(openingBrackets.pop(), bracket)) {
                return false;
            }
        }
        return openingBrackets.isEmpty();
    }

    private static boolean isSimilarBrackets(char openBracket, char closingBracket) {
        switch (closingBracket) {
            case ']':
                if (openBracket != '[') {
                    return false;
                }
                break;
            case '}':
                if (openBracket != '{') {
                    return false;
                }
                break;
            case '>':
                if (openBracket != '<') {
                    return false;
                }
                break;
            case ')':
                if (openBracket != '(') {
                    return false;
                }
                break;
        }
        return true;
    }
}
