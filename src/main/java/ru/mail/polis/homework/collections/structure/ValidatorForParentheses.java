package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    static HashMap<Character, Character> hm = new HashMap<>();

    static {
        hm.put('(', ')');
        hm.put('{', '}');
        hm.put('[', ']');
        hm.put('<', '>');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean wasBracket = false;
        Stack<Character> closersStack = new Stack<>();
        for (char bracketCandidate : value.toCharArray()) {
            if (hm.containsKey(bracketCandidate)) { //O(1)
                closersStack.push(hm.get(bracketCandidate));
                wasBracket = true;
            } else {
                if (!closersStack.isEmpty() && closersStack.peek() == bracketCandidate) {
                    closersStack.pop();
                }
            }
        }
        return closersStack.size() == 0 && wasBracket;
    }

}
