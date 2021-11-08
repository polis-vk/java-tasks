package ru.mail.polis.homework.collections.structure;

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

    /* Map, содержащая пару символ скобки и условный номер, соответствующий ей.
       Нечётные номера - открывающие скобки, чётные - закрывающие.
       Номера скобок одной категории (к примеру, '(' и ')') отличаются на единицу */
    private static final Map<Character, Integer> mapOfBrackets = Map.of(
            '(', 1,
            ')', 2,
            '[', 3,
            ']', 4,
            '{', 5,
            '}', 6,
            '<', 7,
            '>', 8);

    public static boolean validate(String value) {
        if (value == null || value.equals("") || !containsBrackets(value)) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        char currentSymbol;
        for (int i = 0; i < value.length(); i++) {
            currentSymbol = value.charAt(i);
            if (mapOfBrackets.get(currentSymbol) == null) {
                continue;
            }
            int currentAccessToMap = mapOfBrackets.get(currentSymbol);
            if (currentAccessToMap % 2 != 0) {
                stack.push(currentAccessToMap);
            } else if (stack.size() == 0 || currentAccessToMap != stack.pop() + 1) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean containsBrackets(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (mapOfBrackets.get(value.charAt(i)) != null) {
                return true;
            }
        }
        return false;
    }
}
