package ru.mail.polis.homework.collections.structure;

import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.LinkedList;

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
        // For my point of view, empty string is CORRECT parentheses sequence,
        // but tests insist on opposite
        if (value == null) {
            return false;
        }
        Stack<Character> openParentheses = new Stack<>();
        class Local {
            boolean isLastOpenIsReversedTo(Character c) {
                if (openParentheses.isEmpty()) {
                    return false;
                }
                Character topElement = openParentheses.peek();
                switch (c) {
                    case '}':
                        return topElement == '{';
                    case ')':
                        return topElement == '(';
                    case ']':
                        return topElement == '[';
                    case '>':
                        return topElement == '<';
                    default:
                        throw new NoSuchElementException("This method accepts only parentheses");
                }
            }
        }

        boolean hasAtListOneOpenParenthesis = false;
        for (Character c : value.toCharArray()) {
            switch (c) {
                case '{':
                case '[':
                case '<':
                case '(':
                    hasAtListOneOpenParenthesis = true;
                    openParentheses.push(c);
                    break;
                case '}':
                case ')':
                case ']':
                case '>':
                    if (!new Local().isLastOpenIsReversedTo(c)) {
                        return false;
                    }
                    openParentheses.pop();
            }
        }
        return hasAtListOneOpenParenthesis && openParentheses.size() == 0;
    }
}