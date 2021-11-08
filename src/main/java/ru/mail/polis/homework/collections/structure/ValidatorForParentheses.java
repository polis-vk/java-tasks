package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
    private static final Map<Character, Character> bracketsPairs = new HashMap<>();

    static {
        bracketsPairs.put('[', ']');
        bracketsPairs.put('{', '}');
        bracketsPairs.put('<', '>');
        bracketsPairs.put('(', ')');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Stack<Character> brackets = new Stack<>();
        boolean hasBrackets = false;

        for (int i = 0; i < value.length(); ++i) {
            if (bracketsPairs.containsKey(value.charAt(i))) {
                brackets.push(bracketsPairs.get(value.charAt(i)));
                hasBrackets = true;
            }
            if (bracketsPairs.containsValue(value.charAt(i))) {
                hasBrackets = true;
                if (brackets.isEmpty() || brackets.pop() != value.charAt(i)) {
                    return false;
                }
            }
        }

        return hasBrackets && brackets.isEmpty();
    }
}
