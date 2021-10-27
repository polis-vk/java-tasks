package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
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
        if (value == null || value.equals("")) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char currentSymbol;
        char lastSymbol;
        for (int i = 0; i < value.length(); i++) {
            currentSymbol = value.charAt(i);
            if (!isBracket(currentSymbol)) {
                continue;
            }
            if (stack.empty()) {
                if (checkIsRightBracket(currentSymbol)) {
                    return false;
                } else {
                    stack.push(currentSymbol);
                }
            } else {
                lastSymbol = stack.peek();
                if (checkIsLeftBracket(currentSymbol)) {
                    stack.push(currentSymbol);
                } else if (checkIsRightBracket(currentSymbol)) {
                    if (currentSymbol == ')' & lastSymbol == '('
                            || currentSymbol == ']' & lastSymbol == '['
                            || currentSymbol == '}' & lastSymbol == '{'
                            || currentSymbol == '>' & lastSymbol == '<') {
                        stack.pop();
                    } else {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isBracket(char symbol) {
        return checkIsRightBracket(symbol) || checkIsLeftBracket(symbol);
    }

    private static boolean checkIsLeftBracket(char symbol) {
        return symbol == '(' || symbol == '[' || symbol == '{' || symbol == '<';
    }

    private static boolean checkIsRightBracket(char symbol) {
       return symbol == ')' || symbol == ']' || symbol == '}' || symbol == '>';
    }
}
