package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 тугрика.
 * Одна из самых популярных задач.
 * Реализовать метод, который проверяет правильность написания скобок в строке.
 * В строке помимо скобок могут содержаться и другие символы.
 * Скобки могут быть: [],{},<>,()
 * Примеры:
 *      "(-b + (x)^2)/(2+4)" - true
 *      "Понедельники меня угнетают ((" - false
 *
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Stack<Character> parenthesesStack = new Stack<>();
        String openParentheses = "[{<(";
        String closeParentheses = "]}>)";
        boolean hasParentheses = false;

        for (int i = 0; i < value.length(); i++) {
            char symbol = value.charAt(i);

            if (openParentheses.indexOf(symbol) >= 0) {
                parenthesesStack.push(symbol);
                hasParentheses = true;
            } else {
                int symbolIndex = closeParentheses.indexOf(symbol);
                if (symbolIndex >= 0) {
                    if (parenthesesStack.isEmpty()) {
                        return false;
                    }

                    int bracketIndex = openParentheses.indexOf(parenthesesStack.pop());
                    if (symbolIndex != bracketIndex) {
                        return false;
                    }
                }
            }
        }

        return parenthesesStack.isEmpty() && hasParentheses;
    }
}
