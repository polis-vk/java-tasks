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
        int dotCount = str.length() - str.replace(".", "").length();
        int eCount = str.length() - str.replace("e", "").length();
        int minusCount = str.length() - str.replace("-", "").length();
        if (dotCount > 1 || eCount > 1 || minusCount > 2) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (ch == '-' || ch == '.'
                    || ch == 'e' || Character.isDigit(ch)) {
                builder.append(ch);
            }
        }
        if (str.contains("--") || str.contains(".e")
                || str.contains("-e") || str.contains("e.")
                || str.endsWith("-") || str.endsWith("e")
                || str.startsWith("e") || str.startsWith(".")
                || str.contains(".-") || str.contains("-.")) {
            return null;
        }
        if (dotCount + eCount > 0) {
            return parseDouble(builder);
        }
        long result = parseLong(builder.toString());
        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return (int) result;
        }
        return result;
    }
    private static Long parseLong(String number) {
        long res = 0;
        for (char ch : number.toCharArray()) {
            if (Character.isDigit(ch)) {
            res *= 10;
            res += Character.getNumericValue(ch);
        }
        }
         return number.charAt(0) == '-' ? -res : res;
    }
    private static double parseFractional(String number) {
        double res = 0.0;
        double coef = 0.1;
        for (char ch : number.toCharArray()) {
            if (Character.isDigit(ch)) {
            res += Character.getNumericValue(ch) * coef;
            coef /= 10;
        }
        }
        return res;
    }
    private static Double parseDouble(StringBuilder number) {
        int dotIndex = number.indexOf(".");
        int eIndex = number.indexOf("e");
        double res = 0.0;
        if(dotIndex >= 0) {
            res = parseLong(number.substring(0, dotIndex));
            String afterDot = number.substring(dotIndex + 1, eIndex >= 0 ? eIndex : number.length());
            res += parseFractional(afterDot);
        } else {
            res = parseLong(number.substring(0, eIndex));
        }
        if (eIndex >= 0){
            long degree = parseLong(number.substring(eIndex + 1));
            res *= Math.pow(10, degree);
        }
        return res;
    }
        }


