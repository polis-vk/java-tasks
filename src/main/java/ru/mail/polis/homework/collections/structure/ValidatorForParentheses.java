package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Character, Character> brackets = new HashMap<Character, Character>()
    {
            {
                put('(', ')');
                put('[', ']');
                put('{', '}');
                put('<', '>');
            }
    };

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        ArrayDeque<Character> stack = new ArrayDeque<>();
        boolean wereBrackets = false;
        for (int i = 0; i < value.length(); ++i) {
            char curCh = value.charAt(i);
            if (isOpenBracket(curCh)) {
                stack.push(curCh);
                wereBrackets = true;
            } else if (isCloseBracket(curCh)) {
                if (stack.isEmpty() || !isPairedBrackets(stack.pop(), curCh)) {
                    return false;
                }
            }
        }
        return stack.isEmpty() && wereBrackets;
    }

    private static boolean isOpenBracket(char ch) {
        return brackets.containsKey(ch);
    }

    private static boolean isCloseBracket(char ch) {
        return brackets.containsValue(ch);
    }

    private static boolean isPairedBrackets(char ch1, char ch2) {
        return brackets.get(ch1) == ch2;
    }

}
