package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
    private static Map<Character, Character> parenthesesPairs;
    private static Set<Character> openParenthesesTypes;

    static {
        parenthesesPairs = new HashMap<>();
        parenthesesPairs.put(')', '(');
        parenthesesPairs.put('}', '{');
        parenthesesPairs.put(']', '[');
        parenthesesPairs.put('>', '<');
        openParenthesesTypes = new HashSet<>(parenthesesPairs.values());
    }

    public static boolean validate(String value) {
        // For my point of view, empty string is CORRECT parentheses sequence,
        // but tests insist on opposite
        if (value == null) {
            return false;
        }
        Stack<Character> openParentheses = new Stack<>();

        boolean hasAtListOneOpenParenthesis = false;
        for (Character c : value.toCharArray()) {
            if (openParenthesesTypes.contains(c)) {
                hasAtListOneOpenParenthesis = true;
                openParentheses.push(c);
            } else if (parenthesesPairs.containsKey(c)) {
                if (openParentheses.isEmpty()) {
                    return false;
                }
                Character lastOpen = openParentheses.pop();

                if (parenthesesPairs.get(c) != lastOpen) {
                    return false;
                }
            }
        }
        return hasAtListOneOpenParenthesis && openParentheses.size() == 0;
    }

}