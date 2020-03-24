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

        if (str.contains("--") == true || str.contains("-e") == true || str.contains("e.") == true
                || strNew.endsWith("-") == true || strNew.endsWith("e") == true || strNew.endsWith(".") == true)
        {
            return null;
        }

        if (docCount + eCount == 0){
            long longResult = myLongValueOf(strNew);
            if (longResult > Integer.MAX_VALUE || longResult < Integer.MIN_VALUE){
                return myLongValueOf(strNew);
            }
            else {
                return myIntegerValueOf(strNew);
            }
        }

        return Double.valueOf(strNew);
    }

    public static Long myLongValueOf(String str){
        long result = 0;
        if (str.startsWith("-")) {
            str.replace("-", "");
            for (int i = str.length() - 1; i >= 0; i--) {
                result += (Character.digit(str.charAt(i), 10) * Math.pow(10, str.length() - i - 1));
            }
            return new Long(-result);
        }
        else {
            for (int i = str.length() - 1; i >= 0; i--) {
                result += (Character.digit(str.charAt(i), 10) * Math.pow(10, str.length() - i - 1));
            }
            return new Long(result);
        }
    }

    public static Integer myIntegerValueOf(String str){
        int result = 0;
        if (str.startsWith("-")) {
            for (int i = str.length() - 1; i > 0; i--) {
                result += (Character.digit(str.charAt(i), 10) * Math.pow(10, str.length() - i - 1));
            }
            return new Integer(-result);
        }
        else {
            for (int i = str.length() - 1; i >= 0; i--) {
                result += (Character.digit(str.charAt(i), 10) * Math.pow(10, str.length() - i - 1));
            }
            return new Integer(result);
        }
    }
//    public static double myDoubleValueOf(String str){
//        if
//    }
}




