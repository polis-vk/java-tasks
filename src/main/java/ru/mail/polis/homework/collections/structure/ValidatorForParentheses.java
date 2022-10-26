package ru.mail.polis.homework.collections.structure;

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

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }
        Map<Character, Character> CORRECT_PARENTHESES = new HashMap<>();
        CORRECT_PARENTHESES.put('{', '}');
        CORRECT_PARENTHESES.put('<', '>');
        CORRECT_PARENTHESES.put('(', ')');
        CORRECT_PARENTHESES.put('[', ']');
        String stack = "";
        boolean parenthesesFound = false;
        for (int i = 0; i < value.length(); i++) {
            if (CORRECT_PARENTHESES.containsKey(value.charAt(i))) {
                stack = stack + value.charAt(i);
                parenthesesFound = true;
            } else try {
                if (CORRECT_PARENTHESES.containsValue(value.charAt(i))) {
                    if (CORRECT_PARENTHESES.get(stack.charAt(stack.length() - 1)) == value.charAt(i)) {
                        stack = stack.substring(0, stack.length() - 1);
                    } else {
                        return false;
                    }
                }
            } catch (StringIndexOutOfBoundsException exception) {
                return false;
            }
        }
        return stack.isEmpty() && parenthesesFound;
    }
}
