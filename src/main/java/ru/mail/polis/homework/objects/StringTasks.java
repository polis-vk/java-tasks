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
        if (str == null || str.isEmpty()){
            return null;
        }

        int pointCount = str.length() - str.replace(".", "").length();
        int eCount = str.length() - str.replace("e", "").length();
        int minusCount = str.length() - str.replace("-", "").length();
        if (pointCount > 1 || eCount > 1  || minusCount >2){
            return null;
        }

        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()){
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.'){
                digitStringBuilder.append(c);
            }
        }

        String digitString = digitStringBuilder.toString();
        if (digitString.isEmpty()){
            return null;
        }
        if (digitString.length() - digitString.replace("--", "").length() != 0
                ||digitString.length() - digitString.replace("-e", "").length() != 0
                ||digitString.length() - digitString.replace("-.", "").length() != 0
                || digitString.endsWith("-")){
            return null;
        }

        if (pointCount + eCount == 0){
            long longResult = longValueOfString(digitString);
            if (longResult > Integer.MAX_VALUE || longResult < Integer.MIN_VALUE){
                return longResult;
            }
            return (int) longResult;
        }
        return doubleValueOfString(digitString);
    }

    public static double doubleValueOfString(String str){
        if (str == null || str.isEmpty()){
            return 0;
        }

        boolean isSigned = false;
        if (str.startsWith("-")){
            isSigned = true;
        }
        boolean expIsSigned = false;
        if (str.contains("e-")){
            expIsSigned = true;
        }

        String unsignedString;
        if (isSigned){
            unsignedString = str.substring(1);
        } else{
            unsignedString = str;
        }
        int indexOfExp = unsignedString.indexOf('e');
        int indexOfPoint = unsignedString.indexOf('.');
        double result;
        if (indexOfExp >=0) {
            double mantissa = stringToDouble(unsignedString.substring(0, indexOfExp));
            long exponent;
            if (expIsSigned){
                exponent = stringToLong(unsignedString.substring(indexOfExp+2));
            } else {
                exponent = stringToLong(unsignedString.substring(indexOfExp+1));
            }
            if (expIsSigned) {
                result = mantissa / Math.pow(10, exponent);
            } else {
                result = mantissa * Math.pow(10, exponent);
            }
        } else {
            long temp = stringToLong(unsignedString);
            result = (double) temp / Math.pow(10, unsignedString.length() - indexOfPoint - 1);
        }
        if (isSigned) {
            return -result;
        }
        return result;
    }

    public static long longValueOfString(String str){
        if (str == null || str.isEmpty()) {
            return 0;
        }
        if (str.startsWith("-")){
            return  - stringToLong(str.substring(1));
        }
        return stringToLong(str);
    }

    public static double stringToDouble(String str){
        int indexOfPoint = str.indexOf('.');
        if (indexOfPoint < 0) {
            return (double) stringToLong(str);
        }
        return (double) stringToLong(str) / Math.pow(10, str.length() - indexOfPoint - 1);
    }

    public static long stringToLong(String str) {
        if (str == null || str.isEmpty()){
            return 0;
        }

        long number = 0;
        char[] charArr = str.toCharArray();
        int exponent = 0;
        for (int i = str.length()-1; i >= 0 ; i--) {
            number += (long) (charArr[i] - '0') * Math.pow(10, exponent++);
        }
        return number;
    }
}
