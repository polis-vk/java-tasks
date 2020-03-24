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
        int pointCounts = symbolCount(str, ".");
        int eCounts = symbolCount(str, "e");
        int minusCounts = symbolCount(str, "-");
        int doubleSeparators = pointCounts + eCounts;
        if (pointCounts > 1 || eCounts > 1 || doubleSeparators > 2 || minusCounts > 2) {
            return null;
        }
        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }
        String digitString = digitStringBuilder.toString();
        if ((symbolCount(digitString, "--") != 0)
                || (symbolCount(digitString, "-e") != 0)
                || (symbolCount(digitString, ".e") != 0)) {
            return null;
        }
        if (digitString.indexOf("-") == (digitString.length() - 1)) {
            return null;
        }

        if (doubleSeparators == 0) {
            long longResult = strToLong(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longResult;
        }
        return strToDouble(digitString);

    }

    private static int symbolCount(String str, String symbol) {
        return str.length() - str.replace(symbol, "").length();
    }

    private static long strToLong(String str) {
        long result = 0;
        if (str.charAt(0) == '-') {
            for (int i = 1; i < str.length(); i++) {
                result *= 10;
                result += str.charAt(i) - 48;
            }
            return result * -1;
        }
        for (int i = 0; i < str.length(); i++) {
            result *= 10;
            result += str.charAt(i) - 48;
        }
        return result;
    }

    private static double strToDouble(String str) {
        int pointPos = str.indexOf(".");
        int ePos = str.indexOf("e");
        double pow = strToLong(str.substring(ePos + 1));
        double number;
        double numberBeforePoint;
        double numberAfterPoint = 0;
        double afterPointPow = 0;
        if (pointPos == -1) {
            number = strToLong(str.substring(0, ePos));
        } else {
            if (ePos != -1) {
                for (int i = pointPos + 1; i < ePos; i++) {
                    numberAfterPoint *= 10;
                    numberAfterPoint += str.charAt(i) - 48;
                    afterPointPow--;
                }
            } else {
                pow = 0;
                for (int i = pointPos + 1; i < str.length(); i++) {
                    numberAfterPoint *= 10;
                    numberAfterPoint += str.charAt(i) - 48;
                    afterPointPow--;
                }
            }
            numberBeforePoint = strToLong((str.substring(0, pointPos)));
            number = numberBeforePoint + numberAfterPoint * Math.pow(10, afterPointPow);
        }
        return number * Math.pow(10, pow);
    }


}
