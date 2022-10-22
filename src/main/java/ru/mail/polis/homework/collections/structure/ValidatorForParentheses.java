package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Stack;

/**
 * Задание оценивается в 2 тугрика.
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
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char currentBracket;
        boolean thereWasBracket = false;
        for (int index = 0; index < value.length(); index++) {
            currentBracket = value.charAt(index);
            if (!Arrays.asList('(', '[', '{', '<').contains(currentBracket)) {
                if (Arrays.asList(')', ']', '}', '>').contains(currentBracket)) {
                    thereWasBracket = true;
                    if (!stack.isEmpty() && check(stack.peek(), currentBracket)) {
                        stack.pop();
                    } else {
                        stack.push(currentBracket);
                        break;
                    }
                }
            } else {
                thereWasBracket = true;
                stack.push(currentBracket);
            }
        }
        return stack.isEmpty() && thereWasBracket;
    }

    public static boolean check(Character openingBracket, Character closingBracket) {
        return (openingBracket == '(' && closingBracket == ')') || (openingBracket == '[' && closingBracket == ']') ||
                (openingBracket == '{' && closingBracket == '}') || (openingBracket == '<' && closingBracket == '>');
    }
}
