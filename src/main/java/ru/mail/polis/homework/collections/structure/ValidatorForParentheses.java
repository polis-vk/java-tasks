package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Character, Character> bracketMatcher;

    static {
        bracketMatcher = new HashMap<>();

        bracketMatcher.put(']', '[');
        bracketMatcher.put('}', '{');
        bracketMatcher.put('>', '<');
        bracketMatcher.put(')', '(');
    }

    private static boolean isOpenBracket(char bracket) {
        return bracketMatcher.containsValue(bracket);
    }

    private static boolean isCloseBracket(char bracket) {
        return bracketMatcher.containsKey(bracket);
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean isBracketsExist = false;
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : value.toCharArray()) {
            if (isOpenBracket(ch)) {
                isBracketsExist = true;
                stack.add(ch);
            } else if (isCloseBracket(ch)) {
                if (stack.isEmpty() || !stack.removeLast().equals(bracketMatcher.get(ch))) {
                    return false;
                }
            }
        }

        return isBracketsExist && stack.isEmpty();
    }
}
