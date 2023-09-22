package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
        if(value == null || value.isEmpty()) return false;
        Stack<Character> stack = new Stack<>();
        int bracket_count = 0;
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (c == '{') { ++bracket_count; stack.push('}');}
            else if (c == '[') { ++bracket_count;stack.push(']');}
            else if (c == '(') {++bracket_count;stack.push(')');}
            else if (c == '<') {++bracket_count;stack.push('>');}

            if (c == '}' || c == ']' || c == ')' || c == '>') {
                ++bracket_count;
                if (stack.empty() || c != stack.peek()) return false;
                if (!stack.empty() && c == stack.peek()) stack.pop();
            }
        }
        return stack.empty() && bracket_count != 0;
    }
}
