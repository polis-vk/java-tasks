package ru.mail.polis.homework.collections.structure;

import java.util.*;

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

    private static final Map<Character, Character> brackets = new HashMap<>();
    //private static final char[] openBrackets = new char[]{'(', '{', '[', '<'};
    //private static final char[] closeBrackets = new char[]{')', '}', ']', '>'};

    static {
        brackets.put(')', '(');
        brackets.put('}', '{');
        brackets.put(']', '[');
        brackets.put('>', '<');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return realizeValidate(value);
    }

    private static boolean realizeValidate(String value) {
        //LinkedList<Character> needToClose = new LinkedList<>();//last open bracket stack
        Deque<Character> needToClose = new LinkedList<>();
        boolean bracketChecker = false;
        for (Character symbol : value.toCharArray()) {
            if (brackets.containsValue(symbol)) {
                needToClose.add(symbol);
                bracketChecker = true;
            } else {
                if (brackets.containsKey(symbol)) {
                    if (needToClose.size() == 0 || !needToClose.pollLast().equals(brackets.get(symbol))) {
                        return false;
                    }
                }
            }
        }
        return needToClose.isEmpty() && bracketChecker;
    }

}