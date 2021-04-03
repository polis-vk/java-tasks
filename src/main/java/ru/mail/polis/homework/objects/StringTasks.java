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

        int eCount = (str.split("e").length == 0) ? 0 : str.split("e").length - 1;
        int dotCount = str.length() - str.replace(".", "").length();
        int minusCount = (str.split("-").length == 0) ? 0 : str.split("-").length - 1;
        if (dotCount > 1 || eCount > 1 || minusCount > 2) {
            return null;
        }

        String numberString = strFilter(str);

        if (str.contains("--") || str.contains(".e") || str.contains("-e") || str.contains("e.") || str.endsWith("-") ||
                str.endsWith("e")) {
            return null;
        }
        if (dotCount + eCount > 0) {
            return parseDouble(numberString);
        }
        return parseWhole(numberString);

    }

    private static String strFilter(String str) {
        StringBuilder numberStringBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '.' || ch == 'e' || ch == '-' || Character.isDigit(ch)) {
                numberStringBuilder.append(ch);
            }
        }
        String numberString = numberStringBuilder.toString();
        return numberString;
    }

    private static Number parseWhole(String str) {
        long result = 0;
        for (char ch : str.toCharArray()) {
            if (ch != '-') {
                result = result * 10 + Character.getNumericValue(ch);
            }
        }
        if (str.indexOf('-') == 0) {
            result = -result;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return result;
        }
        return (int) result;
    }

    private static Double parseDouble(String str) {
        int expIndex = str.indexOf('e');
        if (expIndex == -1) {
            return parseRealNumber(str);
        } else {
            Double mantissa = parseRealNumber(str.substring(0, expIndex));
            double order = parseRealNumber(str.substring(expIndex + 1));
            return mantissa * Math.pow(10, order);
        }
    }

    private static Double parseRealNumber(String str) {
        int dotIndex = str.indexOf('.');
        if (dotIndex == -1) {
            return parseWhole(str).doubleValue();
        }

        double doubleWholePart = parseWhole(str.substring(0, dotIndex)).doubleValue();
        String fractionalPart = str.substring(dotIndex + 1);
        double doubleFractionalPart = parseWhole(fractionalPart).doubleValue();
        doubleFractionalPart = doubleFractionalPart * Math.pow(10, -(fractionalPart.length()));
        return doubleWholePart + doubleFractionalPart;
    }
}
