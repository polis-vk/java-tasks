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
        int pointCounts = getSymbolCounts(str, ".");
        int eCounts = getSymbolCounts(str, "e");
        int minusCounts = getSymbolCounts(str, "-");
        boolean isReal = pointCounts + eCounts > 0;

        if (pointCounts > 1 || eCounts > 1 || minusCounts > 2) {
            return null;
        }

        StringBuilder digitStringBuilder = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }

        String digitString = digitStringBuilder.toString();

        if (getSymbolCounts(digitString, "--") != 0 || getSymbolCounts(digitString, "-e") != 0
                || getSymbolCounts(str, "-.") != 0 || digitString.endsWith("-") || digitString.endsWith("e")) {

            return null;
        }

        if (!isReal) {
            long longResult = getLongFormat(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longResult;

        }

        return getDoubleFormat(digitString);
    }

    public static int getSymbolCounts(String str, String s) {
        return str.length() - str.replace(s, "").length();
    }

    public static double getDoubleFormat(String strRealNumber) {

        boolean sign = strRealNumber.startsWith("-");
        boolean signE = strRealNumber.contains("e-");

        String strUnsigned = sign ? (strRealNumber.substring(1)) : strRealNumber;
        int indexE = strUnsigned.indexOf('e');
        int indexPoint = strUnsigned.indexOf('.');
        double result;


        if (indexE != -1) {

            String partBeforeE = strUnsigned.substring(0, indexE);
            String partAfterE = signE ? strUnsigned.substring(indexE + 2) : strUnsigned.substring(indexE + 1);

            double number;
            long exponent = getNumberFromChar(partAfterE);

            if (indexPoint != -1) {
                number = getNumberWithPoint(indexPoint, partBeforeE);

            } else {
                number = getNumberFromChar(partBeforeE);
            }

            if (signE) {
                number /= Math.pow(10, exponent);

            } else {
                number *= Math.pow(10, exponent);

            }
            result = number;
        } else {

            result = getNumberWithPoint(indexPoint, strUnsigned);

        }

        return sign ? -result : result;
    }

    private static double getNumberWithPoint(int indexPoint, String strWithPoint) {
        String partBeforePoint;
        String partAfterPoint;
        double number;

        partBeforePoint = strWithPoint.substring(0, indexPoint);
        partAfterPoint = strWithPoint.substring(indexPoint + 1);

        number = getNumberFromChar(partAfterPoint) / Math.pow(10, partAfterPoint.length());
        number += getNumberFromChar(partBeforePoint);
        return number;
    }


    public static long getNumberFromChar(String strDigits) {
        long exponent = 0;
        long result = 0;
        for (int i = strDigits.length() - 1; i >= 0; i--) {
            result += ((long) strDigits.charAt(i) - '0') * ((long) Math.pow(10, exponent));
            exponent++;
        }
        return result;
    }


    public static long getLongFormat(String strIntegerNumber) {

        boolean sign = strIntegerNumber.startsWith("-");
        String strUnsigned = sign ? (strIntegerNumber.substring(1)) : strIntegerNumber;

        long result = getNumberFromChar(strUnsigned);
        return sign ? -result : result;

    }
}

