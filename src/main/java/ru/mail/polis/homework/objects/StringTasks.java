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
        String intStr = "";
        String fracStr = "";
        String eString = "";
        String[] splitStr = str.split("\\.");
        if (splitStr.length > 2 || splitStr[0].length() == str.length() - 1) {
            return null;
        }
        if (splitStr.length == 1) {
            if (!splitStr[0].contains("e")) {
                intStr = partInteger(splitStr[0]);
            } else {
                String[] currentSplit = splitStr[0].split("e");
                if (currentSplit.length > 2 || currentSplit.length == 1) {
                    return null;
                }
                intStr = partInteger(currentSplit[0]);
                eString = partInteger(currentSplit[1]);
            }
        }
        if (splitStr.length == 2) {
            if (splitStr[0].contains("e")) {
                return null;
            }
            intStr = partInteger(splitStr[0]);
            if (!splitStr[1].contains("e")) {
                fracStr = partFractional(splitStr[1]);
            } else {
                String[] currentSplit = splitStr[1].split("e");
                if (currentSplit.length > 2 || currentSplit.length == 1) {
                    return null;
                }
                fracStr = partFractional(currentSplit[0]);
                eString = partInteger(currentSplit[1]);
            }
        }
        if (intStr == null || fracStr == null || eString == null) {
            return null;
        }
        long resultLong;
        double resultDoubleAndFloat = 0;
        boolean chekMinus = intStr.contains("-");
        intStr = intStr.replaceFirst("-", "");
        if (fracStr.isEmpty() && eString.isEmpty()) {
            resultLong = castToLong(intStr);
            if (chekMinus) {
                resultLong *= -1;
            }
            if (resultLong >= Integer.MIN_VALUE && resultLong <= Integer.MAX_VALUE) {
                return (int) resultLong;
            }
            return resultLong;
        }
        int degree = 0;
        if (!eString.isEmpty()) {
            boolean chekMinusDegree = eString.contains("-");
            eString = eString.replaceFirst("-", "");
            degree = (int) castToLong(eString);
            if (chekMinusDegree) {
                degree *= -1;
            }
        }
        for (int i = 0; i < intStr.length(); i++) {
            resultDoubleAndFloat += charToInt(intStr.charAt(i)) * Math.pow(10, intStr.length() - i - 1);
        }
        if (!fracStr.isEmpty()) {
            for (int i = 0; i < fracStr.length(); i++) {
                resultDoubleAndFloat += charToInt(fracStr.charAt(i)) / Math.pow(10, i + 1);
            }
        }
        resultDoubleAndFloat *= Math.pow(10, degree);
        if (chekMinus) {
            resultDoubleAndFloat *= -1;
        }
        return resultDoubleAndFloat;
    }

    public static long castToLong(String num) {
        long resultNumber = 0;
        for (int i = 0; i < num.length(); i++) {
            resultNumber += charToInt(num.charAt(i)) * Math.pow(10, num.length() - i - 1);
        }
        return resultNumber;
    }

    public static int charToInt(char num) {
        return num - '0';
    }

    private static String partInteger(String str) {
        StringBuilder result = new StringBuilder();
        byte countMinus = 0;
        boolean chekNum = false;
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                chekNum = true;
            }
            if (currentChar == '-') {
                countMinus++;
                if (chekNum || countMinus > 1) {
                    return null;
                }
            }
        }
        if (countMinus == 1) {
            return "-" + result;
        }
        return result.toString();
    }

    private static String partFractional(String str) {
        if (str.contains("-")) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean chekNum = false;
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                result.append(currentChar);
                chekNum = true;
            }
        }
        if (!chekNum) {
            return null;
        }
        return result.toString();
    }

}

