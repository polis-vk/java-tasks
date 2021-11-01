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
        Stack<Character> stack = new Stack<>();
        for (char bracketCandidate : value.toCharArray()) {
            if (hm.containsKey(bracketCandidate)) { //O(1)
                stack.push(bracketCandidate);
                wasBracket = true;
            } else {
                for (Map.Entry<Character, Character> entry : hm.entrySet()) {//O(1)
                    if (entry.getValue() == bracketCandidate) {
                        if (entry.getKey() != stack.pop()) {
                            return false;
                        }
                    }
                }
            }
        }
        return stack.size() == 0 && wasBracket;
    }

}
