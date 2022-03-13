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
        StringBuilder numberBuild = new StringBuilder();
        for (char curChar : str.toCharArray()) {
            if (curChar == '.' || curChar == 'e' || curChar == '-' || Character.isDigit(curChar)) {
                numberBuild.append(curChar);
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
        }
        String numberStr = numberBuild.toString();

        if (numberStr.contains("--") || numberStr.contains("-e") || numberStr.endsWith("e") || numberStr.endsWith("-")) {
            return null;
        }
        if (dotCount > 1 || eCount > 1 || minusCount > 2) {
            return null;
        }
        if (dotCount + eCount >= 1) {
            return ParseDouble(numberStr, dotCount, eCount);
        }

        double result = 0;
        result = (double) ParseNumber(numberStr, result);
        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return (int) result;
        }
        return (long) result;
    }

    public static Number ParseNumber(String numberStr, double result) {
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

    public static Number ParseDouble(String numberStr, int dotCount, int eCount) {
        String strAfterE = numberStr.substring(numberStr.indexOf('e') + 1, numberStr.length());
        double numAfterE = 0;
        if (eCount == 1 && dotCount == 0) {
            double numBeforeE = 0;
            String strBeforeE = numberStr.substring(0, numberStr.indexOf('e'));
            return (double) (ParseNumber(strBeforeE, numBeforeE)) * Math.pow(10.0, (Double) ParseNumber(strAfterE, numAfterE));
        }
        if (eCount == 0 && dotCount == 1) {
            double numBeforeDot = 0;
            double numAfterDot = 0;
            String strBeforeDot = numberStr.substring(0, numberStr.indexOf('.'));
            String strAfterDot = numberStr.substring(numberStr.indexOf('.') + 1, numberStr.length());
            return (double) (ParseNumber(strBeforeDot, numBeforeDot)) + (double) ParseNumber(strAfterDot, numAfterDot) / Math.pow(10, strAfterDot.length());
        }
        double numBeforeDot = 0;
        double numAfterDotToE = 0;
        String strBeforeDot = numberStr.substring(0, numberStr.indexOf('.'));
        String strAfterDotToE = numberStr.substring(numberStr.indexOf('.') + 1, numberStr.indexOf('e'));
        double NumPart1 = (double) ParseNumber(strBeforeDot, numBeforeDot);
        double NumPart2 = (double) ParseNumber(strAfterDotToE, numAfterDotToE) / Math.pow(10, strAfterDotToE.length());
        double NumPart3 = (double) Math.pow(10.0, (Double) ParseNumber(strAfterE, numAfterE));
        return (double) (NumPart1 + NumPart2) * NumPart3;
    }
}
