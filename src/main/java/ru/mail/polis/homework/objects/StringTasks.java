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
        int dotsCounts = getSymbolCounts(str, ".");
        int eCounts = getSymbolCounts(str, "e");
        int minusCounts = getSymbolCounts(str, "-");
        boolean isReal = dotsCounts + eCounts != 0;
        if (dotsCounts > 1 || eCounts > 1 || minusCounts > 2) {
            return null;
        }

        String digitString = deleteRedundantSymbols(str);
        if (getSymbolCounts(digitString, "--") != 0
            || getSymbolCounts(digitString, "-e") != 0
            || getSymbolCounts(digitString, "-.") != 0
            || digitString.endsWith("-")) {
            return null;
        }

        if (!isReal) {
            long longResult = toLong(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longResult;
        }
        return toDouble(digitString);
    }

    private static int getSymbolCounts(String str, String symbol) {
        return str.length() - str.replace(symbol, "").length();
    }

    private static String deleteRedundantSymbols(String str) {
        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }
        return digitStringBuilder.toString();
    }

    private static long toLong(String str) {
        if (str.charAt(0) == '-') {
            return (-1 * parseNumber(str.substring(1)));
        }
        return parseNumber(str);
    }

    /*    private static double toDouble(String str) {
            int dotPosition = str.indexOf(".");
            int ePosition = str.indexOf("e");
            if (dotPosition != -1 && ePosition != -1) {
                double numberBeforeDot = parseDouble(str.substring(0, dotPosition));
                double numberAfterDot = parseDouble(str.substring(dotPosition + 1, ePosition)) / Math.pow(10, str.substring(dotPosition + 1, ePosition).length());
                double eNumber = parseDouble(str.substring(ePosition));
                if (numberBeforeDot < 0) {
                    numberAfterDot *= -1;
                }
                return (numberBeforeDot + numberAfterDot) * eNumber;
            } else if (dotPosition != -1) {
                double numberBeforeDot = parseDouble(str.substring(0, dotPosition));
                double numberAfterDot = parseDouble(str.substring(dotPosition + 1)) / Math.pow(10, str.substring(dotPosition + 1).length());
                return numberBeforeDot + numberAfterDot;
            }
            return parseDouble(str);
        }  */
    private static double toDouble(String str) {
        int dotPosition = str.indexOf(".");
        int ePosition = str.indexOf("e");
        if (dotPosition != -1) {
            double numberBeforeDot = parseDouble(str.substring(0, dotPosition));
            double numberAfterDot = parseDouble(str.substring(dotPosition + 1)) / Math.pow(10, str.substring(dotPosition + 1).length());
            double eNumber = 1;
            if (ePosition != -1) {
                numberAfterDot = parseDouble(str.substring(dotPosition + 1, ePosition)) / Math.pow(10, str.substring(dotPosition + 1, ePosition).length());
                eNumber = parseDouble(str.substring(ePosition));
                if (numberBeforeDot < 0) {
                    numberAfterDot *= -1;
                }
            }
            return (numberBeforeDot + numberAfterDot) * eNumber;
        }
        return parseDouble(str);
    }

    private static long parseNumber(String str) {
        long number = 0;
        for (int i = 0; i < str.length(); i++) {
            number *= 10;
            number += str.charAt(i) - '0';
        }
        return number;
    }

    private static double parseDouble(String str) {
        int multiplier = 1;
        String digitString = str;
        if (str.charAt(0) == '-') {
            digitString = digitString.substring(1);
            multiplier = -1;
        }
        double number = 0;
        for (int i = 0; i < digitString.length(); i++) {
            if (digitString.charAt(i) == 'e') {
                if (number == 0) {
                    number += 1;
                }
                if (digitString.charAt(i + 1) != '-') {
                    number = (number * Math.pow(10, parseNumber(digitString.substring(i + 1))));
                    return number * multiplier;
                }
                number = (number * Math.pow(10, -parseNumber(digitString.substring(i + 2))));
                return number * multiplier;
            }
            number *= 10;
            number += digitString.charAt(i) - '0';
        }
        return number * multiplier;
    }

}
