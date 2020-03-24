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

        int pointCounts = symbolCounts(str, ".");
        int eCounts = symbolCounts(str, "e");
        if (pointCounts > 1 || eCounts > 1 || symbolCounts(str, "-") > 2) {
            return null;
        }

        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }

        String digitString = digitStringBuilder.toString();

        if (symbolCounts(digitString, "--") != 0
                || symbolCounts(digitString, ".e") != 0
                || symbolCounts(digitString, "-e") != 0 || digitString.endsWith("e")
                || digitString.endsWith("-")) {
            return null;
        }

        if (pointCounts == 0 && eCounts == 0) {
            long result = toLong(digitString);
            if (result >= Integer.MIN_VALUE && result <= Integer.MAX_VALUE) {
                return (int) result;
            } else {
                return result;
            }
        } else {
            return toDouble(digitString);
        }
    }

    private static int symbolCounts(String str, String s) {
        return str.length() - str.replace(s, "").length();
    }

    private static long toLong(String str) {
        String copyStr = str;
        int sign = (copyStr.charAt(0) == '-') ? -1 : 1;
        if (sign == -1) {
            copyStr = copyStr.replace("-", "");
        }
        long result = 0;
        for (char digit : copyStr.toCharArray()) {
            result = (result + (digit - '0')) * 10;
        }
        return result * sign / 10;
    }

    private static double toDouble(String str) {
        /*String copyStr = str;
        int sign = (copyStr.charAt(0) == '-') ? -1 : 1;
        if (sign == -1) {
            copyStr = copyStr.replace("-", "");
        }*/

        int pointIndex = str.indexOf('.');
        int eIndex = str.indexOf('e');

        if (pointIndex == -1) {
            long number = toLong(str.substring(0, eIndex));
            long mantissa = toLong(str.substring(eIndex + 1));
            return number * Math.pow(10, mantissa);
        }

        if (eIndex == -1) {
            long beforePoint = toLong(str.substring(0, pointIndex));
            long afterPoint = toLong(str.substring(pointIndex + 1));
            return beforePoint + afterPoint / Math.pow(10, str.length() - pointIndex - 1);
        }

        long beforePoint = toLong(str.substring(0, pointIndex));
        long afterPoint = toLong(str.substring(pointIndex + 1, eIndex));
        long mantissa = toLong(str.substring(eIndex + 1));
        return (beforePoint + afterPoint / Math.pow(10, eIndex - pointIndex - 1)) * Math.pow(10, mantissa);
    }

}
