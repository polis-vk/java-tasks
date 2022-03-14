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

        String[] splitStr = str.split("\\.");
        if (splitStr[0].length() == str.length() - 1) {
            return null;
        }

        if (splitStr.length == 1) {
            if (!splitStr[0].contains("e")) {
                return createIntOrLong(partInteger(splitStr[0]));
            }

            String[] currentSplit = splitStr[0].split("e");
            if (currentSplit.length != 1) {
                return createDoubleOrFloat(partInteger(currentSplit[0]), "", partInteger(currentSplit[1]));
            }
        }

        if (splitStr.length == 2) {
            if (splitStr[0].contains("e")) {
                return null;
            }

            if (!splitStr[1].contains("e")) {
                return createDoubleOrFloat(partInteger(splitStr[0]), partFractional(splitStr[1]), "");
            }

            String[] currentSplit = splitStr[1].split("e");
            if (currentSplit.length != 1) {
                return createDoubleOrFloat(partInteger(splitStr[0]), partFractional(currentSplit[0])
                        , partInteger(currentSplit[1]));
            }
        }

        return null;
    }

    public static Number createIntOrLong(String integerPart) {
        if (integerPart == null) {
            return null;
        }

        long resultLong;
        boolean isNegativeNum = integerPart.contains("-");
        integerPart = integerPart.replaceFirst("-", "");
        resultLong = castToLong(integerPart);
        if (isNegativeNum) {
            resultLong *= -1;
        }

        if (resultLong >= Integer.MIN_VALUE && resultLong <= Integer.MAX_VALUE) {
            return (int) resultLong;
        }

        return resultLong;
    }

    public static Number createDoubleOrFloat(String integerPart, String fractionPart, String exponentPart) {
        if (integerPart == null || fractionPart == null || exponentPart == null) {
            return null;
        }

        double resultDoubleAndFloat = 0;
        boolean isNegativeNum = integerPart.contains("-");
        integerPart = integerPart.replaceFirst("-", "");
        for (int i = 0; i < integerPart.length(); i++) {
            resultDoubleAndFloat += (integerPart.charAt(i) - '0') * Math.pow(10, integerPart.length() - i - 1);
        }

        if (!fractionPart.isEmpty()) {
            for (int i = 0; i < fractionPart.length(); i++) {
                resultDoubleAndFloat += (fractionPart.charAt(i) - '0') / Math.pow(10, i + 1);
            }
        }

        resultDoubleAndFloat *= Math.pow(10, calculateExponent(exponentPart));
        if (isNegativeNum) {
            resultDoubleAndFloat *= -1;
        }

        return resultDoubleAndFloat;
    }

    public static int calculateExponent(String exponentPart) {
        if (exponentPart.isEmpty()) {
            return 0;
        }

        boolean chekMinusDegree = exponentPart.contains("-");
        int degree = (int) castToLong(exponentPart.replaceFirst("-", ""));
        if (chekMinusDegree) {
            degree *= -1;
        }

        return degree;
    }

    public static long castToLong(String num) {
        long resultNumber = 0;
        for (int i = 0; i < num.length(); i++) {
            resultNumber += (num.charAt(i) - '0') * Math.pow(10, num.length() - i - 1);
        }

        return resultNumber;
    }

    private static String partInteger(String str) {
        StringBuilder result = new StringBuilder();
        boolean alreadyHasMinus = false;
        boolean alreadyHasDigits = false;
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                alreadyHasDigits = true;
            }

            if (currentChar == '-') {
                if (alreadyHasMinus || alreadyHasDigits) {
                    return null;
                }

                alreadyHasMinus = true;
            }
        }

        if (alreadyHasMinus) {
            return result.insert(0, "-").toString();
        }

        return result.toString();
    }

    private static String partFractional(String str) {
        if (str.contains("-")) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean isNum = false;
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                isNum = true;
            }
        }

        if (!isNum) {
            return null;
        }

        return result.toString();
    }
}