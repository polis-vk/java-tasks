package ru.mail.polis.homework.collections.structure;

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
        if (value == null || value.equals("")) {
            return false;
        }
        final String bracketsStr = "[{<(]}>)";
        final Stack<Integer> bracketsStack = new Stack<>();
        for (char symb : value.toCharArray()) {
            int bracketIndex = bracketsStr.indexOf(symb);
            if (bracketIndex >= 0 && bracketIndex < 4) {
                bracketsStack.push(bracketIndex);
            } else if (bracketIndex >= 4 && bracketsStack.pop() != bracketIndex % 4) {
                return false;
            }
        }
        return bracketsStack.isEmpty();
    }
}
