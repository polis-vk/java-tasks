package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разреше0нные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    private static int getSymbolCounts(String s, String symbol) {
        return s.length() - s.replace(symbol, "").length();
    }

    public static Number valueOf(String str) {

        if (str == null
                || str.isEmpty()
                || getSymbolCounts(str, ".") > 1
                || getSymbolCounts(str, "e") > 1
                || getSymbolCounts(str, "-") > 2) {
            return null;
        }

        StringBuilder numberString = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)
                    || c == 'e'
                    || c == '-'
                    || c == '.') {
                numberString.append(c);
            }
        }
        String mathStr = numberString.toString();

        if (getSymbolCounts(mathStr, "-e") != 0
                || getSymbolCounts(mathStr, "--") != 0
                || getSymbolCounts(mathStr, ".e") != 0
                || getSymbolCounts(mathStr, "e.") != 0
                || mathStr.endsWith("-")
                || mathStr.endsWith("e")
                || mathStr.endsWith(".")) {
            return null;
        }

        int ePosition = mathStr.indexOf("e");
        int pointPosition = mathStr.indexOf(".");
        double result = 0;
        long number = 0;
        int tenDegree = 0;
        byte minus = 1;
        for (char c : mathStr.toCharArray()) {
            if (c == '-') {
                minus = -1;
                continue;
            }
            if (c == '.') {
                tenDegree = (ePosition == -1) ? (mathStr.length() - 1 - pointPosition) : (ePosition - pointPosition - 1);
                result = number;
                number = 0;
                continue;
            }
            if (c == 'e') {
                result = minus * (result + number / Math.pow(10, tenDegree));
                number = 0;
                minus = 1;
                continue;
            }
            int digit = c - '0';
            number = number * 10 + digit;
        }
        if (ePosition != -1) {
            return result * Math.pow(10, minus * number);
        }
        if (pointPosition != -1) {
            return minus * (result + number / Math.pow(10, tenDegree));
        }
        if (number * minus >= Integer.MIN_VALUE && number * minus <= Integer.MAX_VALUE) {
            return (int) number * minus;
        }
        return number * minus;
    }
}
