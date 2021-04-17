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
        int dotCount = str.split("\\.").length - 1;
        int eCount = str.split("e").length - 1;
        int minusCount = str.split("-").length - 1;
        if (dotCount > 2 || eCount > 2 || minusCount > 3) {
            return null;
        }
        String resStr = clearString(str);
        if (resStr.contains("--") || resStr.contains("-e") || resStr.endsWith("-")
                || resStr.endsWith("e") || resStr.contains("e.") || resStr.contains(".e")) {
            return null;
        }
        if (dotCount + eCount > 0) {
            return doubleNum(resStr);
        }
        long resLongNum = longNum(resStr);
        if (resLongNum < Integer.MIN_VALUE || resLongNum > Integer.MAX_VALUE) {
            return resLongNum;
        }
        return (int) resLongNum;

    }

    public static String clearString(String str) {
        StringBuilder builderNumStr = new StringBuilder();
        for (Character ch : str.toCharArray()) {
            if (ch == '.' || ch == 'e' || ch == '-' || Character.isDigit(ch)) {
                builderNumStr.append(ch);
            }
        }
        return builderNumStr.toString();
    }


    public static double doubleNum(String str) {
        boolean isNegative = false;
        int startIndex = 0;
        if (str.startsWith("-")) {
            isNegative = true;
            startIndex = 1;
        }
        double res = 0;
        if (str.contains(".") && !str.contains("e")) {
            res = convertToRealNumber(str, startIndex);
        }
        if (str.contains("e")) {
            res = convertFromExponential(str, startIndex);
        }
        if (isNegative) {
            return -1 * res;
        }
        return res;
    }

    public static long longNum(String str) {
        boolean isNegative = false;
        int startIndex = 0;
        if (str.startsWith("-")) {
            isNegative = true;
            startIndex = 1;
        }
        long res = (long) convert(str, startIndex);
        if (isNegative) {
            return (-1 * res);
        }
        return res;
    }

    public static double convertToRealNumber(String str, int startIndex) {
        String[] partsOfNum = (str.split("\\."));
        String mainPart = partsOfNum[0];
        String fraction = partsOfNum[1];
        double convertMainPart = convert(mainPart, startIndex);
        double convertFraction = convert(fraction, 0);
        double res = convertMainPart + convertFraction / Math.pow(10, fraction.length());
        return res;
    }

    public static double convertFromExponential(String str, int startIndex) {
        String[] parts = str.split("e");
        String mainPart = parts[0];
        String tenPower = parts[1];
        double mainPartDouble;
        if (mainPart.contains(".")) {
            mainPartDouble = convertToRealNumber(mainPart, startIndex);
        } else {
            mainPartDouble = convert(mainPart, startIndex);
        }
        double p;
        if (tenPower.startsWith("-")) {
            p = convert(tenPower, 1);
            return mainPartDouble / Math.pow(10, p);
        }
        p = convert(tenPower, 0);
        return mainPartDouble * Math.pow(10, p);
    }

    public static double convert(String str, int startIndex) {
        double res = 0;
        int power = 0;
        for (int i = str.length() - 1; i >= startIndex; i--) {
            res += Character.getNumericValue(str.charAt(i)) * Math.pow(10, power);
            power++;
        }
        return res;
    }
}
