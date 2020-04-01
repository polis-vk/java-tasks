package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTestнад примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        int pointCounter = counter(".", str);
        int eCounter = counter("e", str);
        if (pointCounter > 1 || eCounter > 1 || counter("-", str) > 2) {
            return null;
        }

        StringBuilder newStringBuilder = new StringBuilder();
        for (char symbol : str.toCharArray()) {
            if (Character.isDigit(symbol) || symbol == 'e' || symbol == '.' || symbol == '-') {
                newStringBuilder.append(symbol);
            }
        }

        String newString = newStringBuilder.toString();
        if (counter("--", newString) > 0
                || counter("-.", newString) > 0
                || counter("-e", newString) > 0
                || newString.endsWith(".")
                || newString.endsWith("e")
                || newString.endsWith("-")) {
            return null;
        }

        if (pointCounter + eCounter == 0) {
            long result = strToLong(newString);
            if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
                return (int) result;
            }
            return result;
        }
        return strToDouble(newString, pointCounter, eCounter);
    }

    private static double strToDouble(String newString, int pointCounter, int eCounter) {
        String beforeSeparator;
        String afterPoint;
        String afterExp;
        if (pointCounter == 0) {
            beforeSeparator = newString.substring(0, newString.indexOf('e'));
            afterPoint = "0";
            afterExp = newString.substring(newString.indexOf('e') + 1);
        } else {
            beforeSeparator = newString.substring(0, newString.indexOf('.'));
            if (eCounter == 0) {
                afterPoint = newString.substring(newString.indexOf('.') + 1);
                afterExp = "0";
            } else {
                afterPoint = newString.substring(newString.indexOf('.') + 1, newString.indexOf('e'));
                afterExp = newString.substring(newString.indexOf('e') + 1);
            }
        }
        long beforeSepLong = strToLong(beforeSeparator);
        long afterPointLong = strToLong(afterPoint);
        long afterExpLong = strToLong(afterExp);
        return (beforeSepLong + afterPointLong * Math.pow(10, -1 * afterPoint.length()))
                * Math.pow(10, afterExpLong);
    }

    private static long strToLong(String newString) {
        int sign = 1;
        String tempString = newString;
        if (tempString.charAt(0) == '-') {
            sign *= -1;
            tempString = tempString.substring(1);
        }
        long result = 0;
        for (char symbol : tempString.toCharArray()) {
            result *= 10;
            result += symbol - '0';
        }
        return result * sign;
    }

    private static int counter(String symbol, String str) {
        return str.length() - str.replace(symbol, "").length();

    }
}
