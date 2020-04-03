package ru.mail.polis.homework.objects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        int pointCounts = getSymbolCounts(str, ".");
        int eCounts = getSymbolCounts(str, "e");
        int minusCounts = getSymbolCounts(str, "-");

        if (pointCounts > 1 || eCounts > 1 || minusCounts > 2) {
            return null;
        }

        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }
        String digitString = digitStringBuilder.toString();

        if (getSymbolCounts(digitString, "--") != 0
                || getSymbolCounts(digitString, "-e") != 0
                || getSymbolCounts(digitString, "-.") != 0
                || digitString.endsWith("-")
                || digitString.endsWith("e")) {
            return null;
        }

        int doubleSeparators = pointCounts + eCounts;
        if (doubleSeparators == 0) {
            long longResult = longValueOf(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longResult;
        }

        return doubleValueOf(digitString);
    }

    private static long longValueOf(String digitString) {
        String absValue = getAbsValue(digitString);

        long result = 0L;

        for (int i = 0; i < absValue.length(); i++) {
            result += absValue.charAt(i) - '0';
            if (i + 1 < absValue.length()) {
                result *= 10;
            }
        }

        if (digitString.startsWith("-")) {
            result *= -1;
        }

        return result;
    }

    private static Double doubleValueOf(String digitString) {
        String absValue = getAbsValue(digitString);
        boolean isPoint = false;
        int eIndex = absValue.indexOf('e');
        eIndex = eIndex == -1 ? absValue.length() : eIndex;
        long order = 0;
        long mantissa = 0;

        for (int i = 0; i < eIndex; i++) {
            if (absValue.charAt(i) == '.') {
                isPoint = true;
                if (i + 1 == absValue.length()){
                    mantissa /= 10;
                }
            } else {
                mantissa += absValue.charAt(i) - '0';
                if (i + 1 < eIndex) {
                    mantissa *= 10;
                }
                if (isPoint) {
                    order -= 1;
                }
            }
        }

        if (eIndex != absValue.length()) {
            order += longValueOf(absValue.substring(eIndex + 1));
        }

        if (digitString.startsWith("-")) {
            mantissa *= -1;
        }

        if (order < 0) {
            return mantissa / Math.pow(10, Math.abs(order));
        } else {
            return mantissa * Math.pow(10, order);
        }
    }

    private static String getAbsValue(String digitString) {
        if (digitString.startsWith("-")) {
            return digitString.substring(1);
        } else {
            return digitString;
        }
    }


    public static int getSymbolCounts(String str, String s) {
        return str.length() - str.replace(s, "").length();
    }
}
