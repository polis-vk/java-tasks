package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        int dotCount = 0;
        int eCount = 0;
        int minusCount = 0;
        char prevChar = ' ';
        StringBuilder numberBuild = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (curChar == '.' || curChar == 'e' || curChar == '-' || Character.isDigit(curChar)) {
                numberBuild.append(str.charAt(i));
            }
            if (curChar == '.') {
                dotCount++;
            }
            if (curChar == 'e') {
                eCount++;
            }
            if (curChar == '-') {
                minusCount++;
            }
            if (dotCount > 1 || eCount > 1 || minusCount > 2) {
                return null;
            }
            if (Character.isDigit(prevChar) && curChar == '-') {
                return null;
            }
            if (prevChar == '-' && curChar == '-') {
                return null;
            }
            if (prevChar == '-' && curChar == 'e') {
                return null;
            }
            prevChar = curChar;
        }
        String numberStr = numberBuild.toString();

        if (str.charAt(str.length() - 1) == 'e' || str.charAt(str.length() - 1) == '.' || str.charAt(str.length() - 1) == '-') {
            return null;
        }
        if (dotCount + eCount >= 1) {
            return parseDouble(numberStr, dotCount, eCount);
        }
        double result = parseNumber(numberStr);
        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return (int) result;
        }
        return (long) result;
    }

    public static double parseNumber(String numberStr) {
        double result = 0;
        for (int i = 1; i < numberStr.length(); i++) {
            result += Character.digit(numberStr.charAt(i), 10) * Math.pow(10, numberStr.length() - i - 1);
        }
        if (numberStr.charAt(0) != '-') {
            result += (Character.digit(numberStr.charAt(0), 10) * Math.pow(10, numberStr.length() - 1));
        } else {
            result = -result;
        }
        return result;
    }

    public static double parseDouble(String numberStr, int dotCount, int eCount) {

        String strAfterE = numberStr.substring(numberStr.indexOf('e') + 1);
        int indexOfE = numberStr.indexOf('e');

        if (eCount == 1 && dotCount == 0) {
            String strBeforeE = numberStr.substring(0, indexOfE);
            return parseNumber(strBeforeE) * Math.pow(10, parseNumber(strAfterE));
        }

        int indexOfDot = numberStr.indexOf('.');
        if (eCount == 0 && dotCount == 1) {
            String strBeforeDot = numberStr.substring(0, indexOfDot);
            String strAfterDot = numberStr.substring(indexOfDot + 1);
            return parseNumber(strBeforeDot) + parseNumber(strAfterDot) / Math.pow(10, strAfterDot.length());
        }

        String strBeforeDot = numberStr.substring(0, indexOfDot);
        String strAfterDotToE = numberStr.substring(indexOfDot + 1, indexOfE);
        double numPart1 = parseNumber(strBeforeDot) + parseNumber(strAfterDotToE) / Math.pow(10, strAfterDotToE.length());
        double numPart2 = Math.pow(10, parseNumber(strAfterE));
        return numPart1 * numPart2;
    }
}
