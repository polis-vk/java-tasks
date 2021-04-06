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
        StringBuilder numberStringBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '.' || ch == 'e' || ch == '-' || Character.isDigit(ch)) {
                numberStringBuilder.append(ch);
            }
        }
        String numberString = numberStringBuilder.toString();
        if (numberString.contains("--") || numberString.contains("-e") || numberString.contains(".e") ||
                numberString.contains("e.") || numberString.endsWith("-") || numberString.endsWith("e") ||
                numberString.endsWith(".")) {
            return null;
        }
        if (dotCount + eCount > 0) {
            return parseDouble(numberString);
        }
        long longNumber = parseLong(numberString);
        if (longNumber > Integer.MAX_VALUE || longNumber < Integer.MIN_VALUE) {
            return longNumber;
        }
        return (int)longNumber;
    }

    private static long parseLong(String str) {
        long ans = 0;
        for (int i = str.length() - 1; i > 0; i--) {
            ans += (str.toCharArray()[i] - '0') * Math.pow(10, str.length() - i - 1);
        }
        if (str.toCharArray()[0] == '-') {
            ans = -ans;
        } else {
            ans += (str.toCharArray()[0] - '0') * Math.pow(10, str.length() - 1);
        }
        return ans;
    }

    private static double parseDouble(String str) {
        int dotNumber = str.indexOf('.');
        int eNumber = str.indexOf('e');
        long beforeDot = 0;
        long afterDot = 0;
        long afterE = 0;
        if (dotNumber > 0) {
            beforeDot = parseLong(str.substring(0, dotNumber));
            if (eNumber > 0) {
                afterDot = parseLong(str.substring(dotNumber + 1, eNumber));
            } else {
                afterDot = parseLong(str.substring(dotNumber + 1));
            }
        } else {
            beforeDot = parseLong(str.substring(0, eNumber));
        }
        if (eNumber > 0) {
            afterE = parseLong(str.substring(eNumber + 1));
        }
        double afterDotValue = 0;
        if (dotNumber > 0) {
            afterDotValue = afterDot * Math.pow(10, -Math.ceil(Math.log10(afterDot)));
        }
        return (beforeDot + afterDotValue) * Math.pow(10, afterE);
    }
}


