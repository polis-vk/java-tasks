package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
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

    private static final Map<Character, Character> brackets;

    static {
        brackets = new HashMap<>();
        brackets.put('[', ']');
        brackets.put('{', '}');
        brackets.put('<', '>');
        brackets.put('(', ')');
    }


    public static boolean validate(String value) {
        boolean bracketFlag = false;
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (char ch : value.toCharArray()) {
            if (!isBracket(ch)) {
                continue;
            }
            bracketFlag = true;
            if (brackets.containsKey(ch)) {
                stack.push(ch);
            } else {
                if (stack.isEmpty() || ch != brackets.get(stack.lastElement())) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return bracketFlag && stack.isEmpty();
    }

    private static boolean isBracket(char c) {
        return brackets.containsKey(c) || brackets.containsValue(c);
    }

}
