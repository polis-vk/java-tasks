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
 * "(-b + (x)^2)/(2+4)" - true
 * "Понедельники меня угнетают ((" - false
 * <p>
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {
    private static final Map<Character, Character> brackTypes = new HashMap<Character, Character>();

    static {
        brackTypes.put(']', '[');
        brackTypes.put('}', '{');
        brackTypes.put('>', '<');
        brackTypes.put(')', '(');
    }

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }

        boolean valHasBracket = false;
        Stack<Character> brStack = new Stack<>();
        for (char sym : value.toCharArray()) {
            if (brackTypes.containsValue(sym)) {
                brStack.push(sym);
                if (!valHasBracket) {
                    valHasBracket = true;
                }
            } else if (brackTypes.containsKey(sym)) {
                if (!brStack.isEmpty() && !brackTypes.get(sym).equals(brStack.pop())) {
                    return false;
                }
            }
        }

        if (valHasBracket) {
            return brStack.size() == 0;
        }
        return false;
    }
}