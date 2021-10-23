package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
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

        Stack<BracketsType> brStack = new Stack<>();
        for (char sym : value.toCharArray()) {
            if (BracketsType.isOpenBracket(sym)) {
                brStack.push(BracketsType.getType(sym));
            } else if (BracketsType.isCloseBracket(sym)) {
                if (!brStack.pop().equals(BracketsType.getType(sym))) {
                    return false;
                }
            }
        }

        return brStack.size() == 0;
    }


    private enum BracketsType {
        SQUARE,
        FIGURE,
        TRIANGLE,
        ROUND,
        NOT_OPEN_BRACKET;

        public static boolean isOpenBracket(char bracket) {
            if (bracket == '[' || bracket == '{' || bracket == '<' || bracket == '(') {
                return true;
            }
            return false;
        }

        public static boolean isCloseBracket(char bracket) {
            if (bracket == ']' || bracket == '}' || bracket == '>' || bracket == ')') {
                return true;
            }
            return false;
        }

        public static BracketsType getType(char bracket) {
            switch (bracket) {
                case '[':
                case ']':
                    return SQUARE;
                case '{':
                case '}':
                    return FIGURE;
                case '<':
                case '>':
                    return TRIANGLE;
                case '(':
                case ')':
                    return ROUND;
                default:
                    return NOT_OPEN_BRACKET;
            }
        }
    }

}
