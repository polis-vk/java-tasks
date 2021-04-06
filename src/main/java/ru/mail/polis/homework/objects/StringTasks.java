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
    private static final char[] SYMBOLS = { '-', '.', 'e' };

    public static Number valueOf(String str) {

        if (str == null || str.isEmpty()) {
            return null;
        }

        int dotCount = str.length() - str.replace(".", "").length();
        int expCount = str.length() - str.replace("e", "").length();
        int subCount = str.length() - str.replace("-", "").length();

        if (dotCount > 1 || expCount > 1 || subCount > 2) {
            return null;
        }

        StringBuilder strBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (searchSymbols(ch, SYMBOLS) || Character.isDigit(ch)) {
                strBuilder.append(ch);
            }
        }

        if (str.startsWith("e") || str.startsWith(".")
                || str.contains("-e") || str.contains(".e")
                || str.contains("e.") || str.contains("--")
                || str.contains(".-") || str.contains("-.")
                || str.endsWith("-") || str.endsWith("e")) {
            return null;
        }

        if (dotCount + expCount > 0) {
            return parseDouble(strBuilder);
        }

        long result = parseLong(strBuilder.toString());

        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return (int) result;
        }

        return result;
    }

    private static Double parseDouble(StringBuilder numberStr) {
        int eIndex = numberStr.indexOf("e");
        int dotIndex = numberStr.indexOf(".");

        double result = 0.0;

        if (dotIndex >= 0) {
            result = parseLong(numberStr.substring(0, dotIndex));
            String afterDot = numberStr.substring(dotIndex + 1, eIndex >= 0 ? eIndex : numberStr.length());
            result += parseFractional(afterDot);
        } else {
            result = parseLong(numberStr.substring(0, eIndex));
        }

        if (eIndex >= 0) {
            long degree = parseLong(numberStr.substring(eIndex + 1));
            result *= Math.pow(10, degree);
        }

        return result;
    }

    private static double parseFractional(String numberStr) {
        double result = 0.0;
        double coefficient = 0.1;
        for (char ch : numberStr.toCharArray()) {
            if (Character.isDigit(ch)) {
                result += Character.getNumericValue(ch) * coefficient;
                coefficient /= 10;
            }
        }
        return result;
    }

    private static Long parseLong(String numberStr) {
        long result = 0;

        for (char ch : numberStr.toCharArray()) {
            if (Character.isDigit(ch)) {
                result *= 10;
                result += Character.getNumericValue(ch);
            }
        }
        if (numberStr.charAt(0) == '-') {
            return -result;
        } else {
            return result;
        }
    }

    private static boolean searchSymbols(char element, char[] symbols) {
        for (char ch : symbols) {
            if (ch == element) {
                return true;
            }
        }
        return false;
    }
}
