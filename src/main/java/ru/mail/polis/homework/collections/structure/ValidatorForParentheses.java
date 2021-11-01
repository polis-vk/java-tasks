package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import jdk.nashorn.internal.ir.annotations.Immutable;

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

    @Immutable
    private static final Map<Character, Character> OPEN_BRACKETS = new HashMap<>();

    static {
        OPEN_BRACKETS.put(')', '(');
        OPEN_BRACKETS.put(']', '[');
        OPEN_BRACKETS.put('>', '<');
        OPEN_BRACKETS.put('}', '{');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return realizeValidate(value);
    }

    private static boolean realizeValidate(String value) {
        Deque<Character> needToClose = new LinkedList<>();
        boolean hasAnyBracket = false;
        for (Character symbol : value.toCharArray()) {
            if (OPEN_BRACKETS.containsValue(symbol)) {
                needToClose.add(symbol);
                hasAnyBracket = true;
            } else {
                if (OPEN_BRACKETS.containsKey(symbol)) {
                    if (needToClose.size() == 0 || !needToClose.pollLast().equals(OPEN_BRACKETS.get(symbol))) {
                        return false;
                    }
                }
            }
        }
        return hasAnyBracket && needToClose.isEmpty();
    }

}
