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
        boolean isReal = pointCounts + eCounts == 0;

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

        if (getSymbolCounts(str, "--") != 0 || getSymbolCounts(str, "-e") != 0
                || getSymbolCounts(str, "-.") != 0 || digitString.endsWith("-")) {

            return null;
        }

        if (isReal) {
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
        boolean expForm = strRealNumber.contains("e");
        boolean minusExpForm = strRealNumber.contains("e-");

        String strUnsigned = sign ? (strRealNumber.substring(1)) : strRealNumber;

        double result;
        long exponent;

        if (expForm) {
            double resultExponentPart;
            int indexExp = strUnsigned.indexOf('e');

            String strNotExp = strUnsigned.substring(0, indexExp);
            String strWithExp = minusExpForm ? strUnsigned.substring(indexExp + 2) : strUnsigned.substring(indexExp + 1);

            result = getNumberFromChar(strNotExp)[0];
            exponent = getNumberFromChar(strNotExp)[1];
            result = result * Math.pow(10, -(exponent - 1));
            result = getRound(result, exponent);

            resultExponentPart = getNumberFromChar(strWithExp)[0];
            if (minusExpForm) {
                resultExponentPart = -resultExponentPart;
            }

            result *= Math.pow(10, resultExponentPart);
        } else {

            result = getNumberFromChar(strUnsigned)[0];
            exponent = getNumberFromChar(strUnsigned)[1];
            result = result * Math.pow(10, -(exponent - 1));
            result = getRound(result, exponent);
        }

        return sign ? -result : result;
    }

    public static double getRound(double result, long countDigits) {
        double scale = Math.pow(10, countDigits);
        result = Math.round(result * scale) / scale;
        return result;
    }

    public static long[] getNumberFromChar(String strDigits) {
        long exponent = 0;
        long result = 0;
        for (int i = strDigits.length() - 1; i >= 0; i--) {
            if (strDigits.charAt(i) == '.') {
                continue;
            }
            result += ((long) strDigits.charAt(i) - '0') * ((long) Math.pow(10, exponent));
            exponent++;
        }
        return new long[]{result, exponent};
    }


    public static long getLongFormat(String strIntegerNumber) {

        boolean sign = strIntegerNumber.startsWith("-");
        String strUnsigned = sign ? (strIntegerNumber.substring(1)) : strIntegerNumber;

        long result = getNumberFromChar(strUnsigned)[0];
        return sign ? -result : result;

    }
}

