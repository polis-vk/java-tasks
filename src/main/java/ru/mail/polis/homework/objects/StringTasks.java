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
        int docCount = str.length() - str.replace(".", "").length();
        int eCount = str.length() - str.replace("e", "").length();
        int minusCount = str.length() - str.replace("-", "").length();
        if (docCount > 1 || eCount > 1 || minusCount > 2){
            return null;
        }

        StringBuilder strNewBuilder = new StringBuilder();
        for (char c : str.toCharArray()){
            if (Character.isDigit(c) || c == 'e' || c == '.' || c == '-'){
                strNewBuilder.append(c);
            }
        }
        String strNew = strNewBuilder.toString();

        if (str.contains("--") || str.contains("-e") || str.contains("e.")
                || strNew.endsWith("-") || strNew.endsWith("e") || strNew.endsWith("."))
        {
            return null;
        }

        if (docCount + eCount == 0){
            long longResult = stringToLong(strNew);
            if (longResult > Integer.MAX_VALUE || longResult < Integer.MIN_VALUE){
                return stringToLong(strNew);
            }
            else {
                return (int)longResult;
            }
        }

        return stringToDouble(strNew);
    }

    public static long stringToLong(String str){
        long result = 0;
            for (int i = str.length() - 1; i >= 0 && str.charAt(i) != '-'; i--) {
                result += Character.digit(str.charAt(i), 10) * Math.pow(10, str.length() - i - 1);
            }
        return str.startsWith("-") ? -result : result;
    }

    public static double stringToDouble(String str){
        double sign = str.startsWith("-") ? -1.0 : 1.0;
        if (!str.contains("e")) {
            String strWhole = str.split("\\.")[0];
            String strFractional = str.split("\\.")[1];
            return sign*(Math.abs(stringToLong(strWhole)) + stringToLong(strFractional)*Math.pow(10.0,-strFractional.length()));
        }
        if (!str.contains(".")){
            String strWhole = str.split("e")[0];
            String strDegree = str.split("e")[1];
            return Math.pow(10.0, stringToLong(strDegree))*stringToLong(strWhole);
        }
        String strDegree = str.split("e")[1];
        return Math.pow(10.0, stringToLong(strDegree))*stringToDouble(str.split("e")[0]);
    }

}




