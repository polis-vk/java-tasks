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
    public static Number valueOf(String str) { //1abc-2
        int countPoint = 0;
        int countE = 0;
        int countMinus = 0;
        int countNum = 0;
        StringBuilder newStr = new StringBuilder();
        if (str != null && str.isEmpty()) {
            return null;
        }
        if (str == null) {
            return null;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                countPoint++;
                if ((countPoint > 1 && countE >= 1 && countNum == 0) || i == str.length() - 1) {
                    return null;
                }
                newStr.append(str.charAt(i));
            }
            if (str.charAt(i) == 'e') {
                countE++;
                countMinus = 0;
                countNum = 0;
                newStr.append(str.charAt(i));
                if (countE > 1 || i == str.length() - 1) {
                    return null;
                }
            }
            if (str.charAt(i) == '-') {
                countMinus++;
                newStr.append(str.charAt(i));
                if ((countMinus > 1 || countNum > 0) || i == str.length() - 1) {
                    return null;
                }
            }
            if (Character.isDigit(str.charAt(i))) {
                newStr.append(str.charAt(i));
                countNum++;
            }
        }
        String[] arrStrResult = new String[]{newStr.toString()};
        long degree = 0;
        long resultIntOrLong;
        double resultFloatOrDouble;
        if (countE == 1) {//all part
            arrStrResult = newStr.toString().split("e");
            degree = convertToLong(arrStrResult[1]);
            if(!arrStrResult[0].contains(".")){
                return convertToLong(arrStrResult[0]) * Math.pow(10, degree);
            }
        }
        if (countPoint == 1) {//float or double
            arrStrResult = arrStrResult[0].split("\\.");
            resultFloatOrDouble = convertToLong(arrStrResult[0]);
            resultFloatOrDouble += getPartAfterPoint(arrStrResult[1]);
            return resultFloatOrDouble * Math.pow(10, degree);
        }
        // int or long
        resultIntOrLong = convertToLong(newStr.toString());
        if (resultIntOrLong >= Integer.MIN_VALUE && resultIntOrLong <= Integer.MAX_VALUE) {
            return (int) (resultIntOrLong * Math.pow(10, degree));
        }
        return (long) (resultIntOrLong * Math.pow(10, degree));
    }

    public static long convertToLong(String convertStr) {
        long number = 0;
        boolean isMinus = convertStr.contains("-");
        convertStr = convertStr.replace("-", "");
        StringBuilder str = new StringBuilder(convertStr);
        str.reverse();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                number += (str.charAt(i) - '0') * (long) Math.pow(10, i);
            }
        }
        if (isMinus) {
            number *= -1;
        }
        return number;
    }

    public static double getPartAfterPoint(String str) {
        double number = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                number += (str.charAt(i) - '0') / Math.pow(10, i+1);
            }
        }
        return number;
    }
}
