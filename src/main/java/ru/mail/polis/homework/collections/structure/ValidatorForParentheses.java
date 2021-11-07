package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Задание оценивается в 2 балла.
 * Реализовать метод, который проверяет правильность написания скобок в строке.
 * В строке помимо скобок могут содержаться и другие символы.
 * Скобки могут быть: [],{},<>,()
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {

    private static final Map<Character, Character> brackets = Map.of('(', ')', '[', ']', '{', '}');
    
     public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Deque<Character> deque = new LinkedList<>();
        boolean hasBrackets = false;
        for (Character symbol: value.toCharArray()) {
            if (brackets.containsKey(symbol)) {
                hasBrackets = true;
                deque.addFirst(symbol);
            } else {
                if (brackets.containsValue(symbol)) {
                    if (!deque.isEmpty() && brackets.get(deque.getFirst()).equals(symbol)) {
                        deque.removeFirst();
                    } else {
                        return false;
                    }
                }
            }
        }
        return deque.isEmpty() && hasBrackets;
    }
    
}
