package ru.mail.polis.homework.objects;

import com.sun.tools.javac.util.StringUtils;

import java.util.Objects;

public class StringTasks {


    private static int[] symbolsCounts(StringBuilder str) {
        int[] counts = new int[3];
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '-') {
                counts[0] += 1;
            } else if (str.charAt(i) == 'e') {
                counts[1] += 1;
            } else if (str.charAt(i) == '.') {
                counts[2] += 1;
            }
        }
        return counts;
    }

    private static int inDex(StringBuilder str, char s) {

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == s) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        StringBuilder number = new StringBuilder();
        int lenOfString = str.length();
        for (int i = 0; i < lenOfString; i++) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == 'e' || str.charAt(i) == '.') {
                number.append(str.charAt(i));
            }
        }


        int indexDot = inDex(number, '.');
        int indexE = inDex(number, 'e');
        int[] specialSymbolCounts = symbolsCounts(number);
        int dashCounts = specialSymbolCounts[0];
        int eCounts = specialSymbolCounts[1];
        int dotCounts = specialSymbolCounts[2];
        if (eCounts > 1 || dotCounts > 1 || dashCounts > 2
                || indexDot == 0 || indexDot == number.length()) {
            return null;
        }


        int lenChislo = number.length();
        number.insert(0, '?');
        number.insert(number.length(), '?');
        for (int i = 1; i < lenChislo + 1; i++) {
            if (number.charAt(i) == '-' && (number.charAt(i - 1) == '.' || Character.isDigit(number.charAt(i - 1)) ||
                    !Character.isDigit(number.charAt(i + 1)))) {
                return null;
            }
            if (number.charAt(i) == 'e') {
                if (!Character.isDigit(number.charAt(i - 1)) || number.charAt(i + 1) == '?' || number.charAt(i + 1) == '.') {
                    return null;
                }
            }

            if (number.charAt(i) == '.') {
                if (!Character.isDigit(number.charAt(i - 1)) || !Character.isDigit(number.charAt(i + 1))) {
                    return null;
                }
            }
        }

        if (indexDot != -1 || indexE != -1) {
            return Double.valueOf(number.substring(1, lenChislo + 1));
        }
        if (Integer.MIN_VALUE <= Long.parseLong(number.substring(1, lenChislo + 1)) &&
                Long.parseLong(number.substring(1, lenChislo + 1)) <= Integer.MAX_VALUE) {
            return Integer.parseInt(number.substring(1, lenChislo + 1));
        }
        return Long.valueOf(number.substring(1, lenChislo + 1));

    }
}
