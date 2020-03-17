package ru.mail.polis.homework.objects;

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
        int doubleSeparators = pointCounts + eCounts;
        if (pointCounts > 1 || eCounts > 1 || minusCounts > 2) {
            return null;
        }

        StringBuilder digitStringBuild = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuild.append(c);
            }
        }
        String digitString = digitStringBuild.toString();

        if (getSymbolCounts(digitString, "--") != 0
                || getSymbolCounts(digitString, "-e") != 0
                || getSymbolCounts(digitString, "-.") != 0
                || digitString.endsWith("-")) {
            return null;
        }

        if (doubleSeparators == 0) {
            long longResult = longValueOf(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longValueOf(digitString);
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
        int order = 0;
        double result = 0.0;
        boolean isE = false;
        boolean isPoint = false;
        boolean isMinusAfterE = false;

        for (int i = 0; i < absValue.length(); i++) {
            if (isE) {
                order += absValue.charAt(i) - '0';
                if (i + 1 < absValue.length()) {
                    order *= 10;
                }
            } else if (absValue.charAt(i) == '.') {
                isPoint = true;
            } else if (absValue.charAt(i) == 'e') {
                isE = true;
                isPoint = false;
                result /= Math.pow(10, order);
                order = 0;
                if (absValue.charAt(i + 1) == '-') {
                    isMinusAfterE = true;
                    i++;
                }
            } else {
                result += absValue.charAt(i) - '0';
                if (i + 1 < absValue.length() && absValue.charAt(i + 1) != 'e') {
                    result *= 10;
                }
                if (isPoint) {
                    order += 1;
                }
            }
        }

        if (isE) {
            if (isMinusAfterE) {
                result /= Math.pow(10, order);
            } else {
                result *= Math.pow(10, order);
            }
        } else {
            result /= Math.pow(10, order);
        }

        if (digitString.startsWith("-")) {
            result *= -1;
        }

        return result;
    }

    private static String getAbsValue(String digitString) {
        if (digitString.startsWith("-")) {
            return digitString.substring(1);
        } else {
            return digitString;
        }
    }


    private static int getSymbolCounts(String str, String s) {
        return str.length() - str.replace(s, "").length();
    }
}
