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

        int eCount = str.length() - str.replace("e", "").length();
        int dotCount = str.length() - str.replace(".", "").length();
        int minusCount = str.length() - str.replace("-", "").length();
        if (dotCount > 1 || eCount > 1 || minusCount > 2) {
            return null;
        }

        String numberString = strRemoveExtraChars(str);

        if (str.contains("--") || str.contains(".e") || str.contains("-e") || str.contains("e.") || str.endsWith("-")
                || str.endsWith("e") || str.endsWith(".") || str.startsWith(".") || str.startsWith("e")
                || str.contains("-.") || str.contains(".-")) {
            return null;
        }
        if (dotCount + eCount > 0) {
            return parseDouble(numberString);
        }
        Double numberResult = parseWhole(numberString);
        if (numberResult > Integer.MAX_VALUE || numberResult < Integer.MIN_VALUE) {
            return numberResult.longValue();
        }
        return numberResult.intValue();
    }

    private static String strRemoveExtraChars(String str) {
        StringBuilder numberStringBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '.' || ch == 'e' || ch == '-' || Character.isDigit(ch)) {
                numberStringBuilder.append(ch);
            }
        }
        return numberStringBuilder.toString();
    }

    private static Double parseWhole(String str) {
        double result = 0.0;
        for (char ch : str.toCharArray()) {
            if (ch != '-') {
                result = result * 10 + Character.getNumericValue(ch);
            }
        }
        if (str.charAt(0) == '-') {
            result = -result;
        }
        return result;
    }

    private static Double parseDouble(String str) {
        int expIndex = str.indexOf('e');
        if (expIndex == -1) {
            return parseRealNumber(str);
        }
        double mantissa = parseRealNumber(str.substring(0, expIndex));
        double order = parseDouble(str.substring(expIndex + 1));
        return mantissa * Math.pow(10, order);
    }

    private static Double parseRealNumber(String str) {
        int dotIndex = str.indexOf('.');
        if (dotIndex == -1) {
            return parseWhole(str);
        }

        double doubleWholePart = parseWhole(str.substring(0, dotIndex));
        String strFractionalPart = str.substring(dotIndex + 1);
        double doubleFractionalPart = parseWhole(strFractionalPart);
        doubleFractionalPart = doubleFractionalPart * Math.pow(10, -(strFractionalPart.length()));
        return doubleWholePart + doubleFractionalPart;
    }
}
