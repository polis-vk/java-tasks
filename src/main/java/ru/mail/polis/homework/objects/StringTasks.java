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
        int dotCount = str.length() - str.replace(".", "").length();
        int eCount = str.length() - str.replace("e", "").length();
        int minusCount = str.length() - str.replace("-", "").length();
        if (dotCount > 1 || eCount > 1 || minusCount > 2) {
            return null;
        }
        String numberString = removeUnavailableChars(str);
        if (containsInvalidCombinations(numberString)) {
            return null;
        }
        if (dotCount + eCount > 0) {
            return parseDouble(numberString);
        }
        double number = parseNumber(numberString);
        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            return (long) number;
        }
        return (int) number;
    }

    private static Double parseNumber(String str) {
        int sign = 1;
        int firstAnalyzedChar = 0;
        if (str.charAt(0) == '-') {
            sign = -1;
            firstAnalyzedChar = 1;
        }
        double res = Character.digit(str.charAt(firstAnalyzedChar), 10);

        for (int i = firstAnalyzedChar + 1; i < str.length(); i++) {
            res = res * 10 + Character.digit(str.charAt(i), 10);
        }
        return sign * res;
    }

    private static Double parseDouble(String str) {
        int ePosition = str.indexOf('e');
        int dotPosition = str.indexOf('.');
        int sign = str.charAt(0) == '-' ? -1 : 1;
        boolean containsE = ePosition != -1;
        boolean containsDot = dotPosition != -1;

        double power = containsE ? parseNumber(str.substring(ePosition + 1)) : 0;
        double integerPart;
        double fractionPart;

        if (containsDot) {
            String fractionPartString = str.substring(dotPosition + 1, containsE ? ePosition : str.length());
            integerPart = Math.abs(parseNumber(str.substring(0, dotPosition)));
            fractionPart = Math.abs(parseNumber(fractionPartString)) / Math.pow(10, fractionPartString.length());
        } else {
            integerPart = Math.abs(parseNumber(str.substring(0, ePosition)));
            fractionPart = 0;
        }
        double base = sign * (integerPart + fractionPart);
        return base * Math.pow(10, power);
    }

    private static String removeUnavailableChars(String str) {
        StringBuilder builder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '.' || ch == 'e' || ch == '-' || Character.isDigit(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    private static boolean containsInvalidCombinations(String str) {
        return str.startsWith(".") ||
                str.startsWith("e") ||
                str.contains("--") ||
                str.contains("-e") ||
                str.contains(".e") ||
                str.contains("e.") ||
                str.contains("-.") ||
                str.contains(".-") ||
                str.endsWith("-") ||
                str.endsWith("e") ||
                str.endsWith(".");
    }

}
