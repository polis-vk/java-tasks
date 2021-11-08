package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
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

    public static boolean validate(String value) {
        if ((value == null) || (value.equals(""))) {
            return false;
        }
        boolean isAnyBracketsContained = false;
        Stack<Character> stack = new Stack<>();
        HashMap<Character, Character> map = new HashMap<>();
        map.put(']', '[');
        map.put('}', '{');
        map.put('>', '<');
        map.put(')', '(');
        for (char el : value.toCharArray()) {
            if ((el == '{') || (el == '[') || (el == '<') || (el == '(')) {
                stack.push(el);
                isAnyBracketsContained = true;
            } else if (map.containsKey(el) && !stack.isEmpty() && (!stack.pop().equals(map.get(el)))) {
                return false;
            }
        }
        return (stack.size() == 0) && isAnyBracketsContained;
    }
}