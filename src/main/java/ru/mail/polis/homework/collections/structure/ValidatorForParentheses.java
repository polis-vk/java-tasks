package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;

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

    public static final char[] BRACKETS = new char[] {'(',')','[',']','{','}','<','>'};
    
    public static boolean validate(String value) {
        if (value == null || value.length() < 2) {
            return false;
        }

        boolean valid = false;
        Deque<Character> stack = new ArrayDeque<Character>();
        
        for (int i = 0; i < value.length(); ++i) {
            final char ch = value.charAt(i);
            
            // формально просто свернутый switch
            // т.к. BRACKETS.length = const то метод работает за O(n)
            for (int j = 0; j < BRACKETS.length; ++j) {
                if (ch == BRACKETS[j]) {
                    valid = true;
                    if (j % 2 == 0) {
                        stack.push(ch);
                        continue;
                    } else if (stack.pop() == BRACKETS[j - 1]){
                        continue;
                    }
                    return false;
                }
            }
        }
        return valid && stack.isEmpty();
    }
}
