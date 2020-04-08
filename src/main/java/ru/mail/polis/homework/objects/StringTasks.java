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
        int expCounts = getSymbolCounts(str, "e");
        if (pointCounts > 1
                || expCounts > 1
                || getSymbolCounts(str, "-") > 2) {
            return null;
        }
        String digitStr = stringToDigitString(str);
        if (getSymbolCounts(digitStr, "--") > 0
                || getSymbolCounts(digitStr, "-e") > 0
                || getSymbolCounts(digitStr, "-.") > 0
                || digitStr.endsWith("-")
                || digitStr.endsWith("e")
                || digitStr.endsWith(".")) {
            return null;
        }

        if (pointCounts == 0 && expCounts == 0) {
            long result = stringToLong(digitStr);
            if (result == (int) result) {
                return (int) result;
            } else {
                return result;
            }
        } else {
            return stringToDouble(digitStr, pointCounts, expCounts);
        }
    }

    private static int getSymbolCounts(String str, String symbol) {
        return str.length() - str.replace(symbol, "").length();
    }

    private static long stringToLong(String digitStr) {
        String absDigitStr = (digitStr);
        int sign = 1;
        if (digitStr.charAt(0) == '-') {
            sign = -1;
            absDigitStr = absDigitStr.replace("-", "");
        }
        long result = 0;
        for (char numeral : absDigitStr.toCharArray()) {
            result = (result + (numeral - '0')) * 10;
        }
        return result / sign / 10;
    }

    private static double stringToDouble(String digitStr, int pointCounts, int expCounts) {
        String toPointStr;
        String afterPointStr;
        String afterExpStr;
        if (pointCounts == 0) {
            toPointStr = digitStr.substring(0, digitStr.indexOf("e"));
            afterPointStr = "0";
            afterExpStr = digitStr.substring(digitStr.indexOf("e") + 1);
        } else {
            toPointStr = digitStr.substring(0, digitStr.indexOf("."));
            if (expCounts == 0) {
                afterPointStr = digitStr.substring(digitStr.indexOf(".") + 1);
                afterExpStr = "0";
            } else {
                afterPointStr = digitStr.substring(digitStr.indexOf(".") + 1, digitStr.indexOf("e"));
                afterExpStr = digitStr.substring(digitStr.indexOf("e") + 1);
            }
        }
        long toPointNumb = stringToLong(toPointStr);
        long afterPointNumb = stringToLong(afterPointStr);
        long exponentNumb = stringToLong(afterExpStr);
        return (toPointNumb + afterPointNumb * Math.pow(10, -1 * afterPointStr.length()))
                * Math.pow(10, exponentNumb);
    }
    private static String stringToDigitString(String str) {
        StringBuilder strBuild = new StringBuilder();
        for (char symbol : str.toCharArray()) {
            if (Character.isDigit(symbol)
                    || symbol == 'e'
                    || symbol == '-'
                    || symbol == '.') {
                strBuild.append(symbol);
            }
        }
        return strBuild.toString();
    }
}
