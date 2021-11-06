package ru.mail.polis.homework.collections.structure;


import java.util.HashMap;
import java.util.Map;
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
    private static final Map<Character, Character> parentheses = Map.ofEntries(
            Map.entry('(', ')'),
            Map.entry('[', ']'),
            Map.entry('{', '}'),
            Map.entry('<', '>')
    );

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean parenthesesExist = false;
        Stack<Character> parenthesesStack = new Stack<>();
        for (char sym : value.toCharArray()) {
            Character closed = parentheses.get(sym);
            if (closed != null) {
                parenthesesStack.push(closed);
                parenthesesExist = true;
            }
            if (parentheses.containsValue(sym)) {
                if (parenthesesStack.empty() || !parenthesesStack.peek().equals(sym)) {
                    return false;
                } else {
                    parenthesesStack.pop();
                }
            }
        }
        return parenthesesStack.empty() && parenthesesExist;
    }
}
