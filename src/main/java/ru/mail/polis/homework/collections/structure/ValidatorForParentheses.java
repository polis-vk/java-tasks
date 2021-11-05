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

        boolean valHasBracket = false;
        Stack<Character> brStack = new Stack<>();
        for (char sym : value.toCharArray()) {
            if (isOpenBracket(sym)) {
                brStack.push(sym);
                if (!valHasBracket) {
                    valHasBracket = true;
                }
            } else if (isCloseBracket(sym)) {
                if (!brStack.isEmpty() && !isFitted(brStack.pop(), sym)) {
                    return false;
                }
            }
        }

        if (valHasBracket) {
            return brStack.size() == 0;
        }
        return false;
    }

    private static boolean isFitted(char opBr, char clBr) {
        return (opBr == '[' && clBr == ']') || (opBr == '{' && clBr == '}')
                || (opBr == '<' && clBr == '>') || (opBr == '(' && clBr == ')');
    }

    private static boolean isOpenBracket(char bracket) {
        return bracket == '[' || bracket == '{' || bracket == '<' || bracket == '(';
    }

    private static boolean isCloseBracket(char bracket) {
        return bracket == ']' || bracket == '}' || bracket == '>' || bracket == ')';
    }
}