package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;

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

    private static final HashMap<Character, Character> bracketsMap = new HashMap<>();

    static {
        bracketsMap.put(']', '[');
        bracketsMap.put('}', '{');
        bracketsMap.put('>', '<');
        bracketsMap.put(')', '(');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        ArrayList<Character> stack = new ArrayList<>();
        boolean meetBracket = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '[':
                case '{':
                case '<':
                case '(':
                    meetBracket = true;
                    stack.add(ch);
                    break;
                case ']':
                case '}':
                case '>':
                case ')':
                    if (stack.isEmpty()) {
                        return false;
                    }
                    char bracket = stack.remove(stack.size() - 1);
                    if (bracket != bracketsMap.get(ch)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return stack.isEmpty() && meetBracket;
    }
}
