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
        return Pattern.compile("[({\\[<)}\\]>]+[\\s\\w\\W]*").matcher(value).matches() && realizeValidate(value);
    }

    private static boolean realizeValidate(String value) {
        Deque<Character> deque = new LinkedList<>();
        for (Character symbol : value.toCharArray()) {
            if (brackets.containsKey(symbol)) {
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
        return deque.isEmpty();
    }
}
