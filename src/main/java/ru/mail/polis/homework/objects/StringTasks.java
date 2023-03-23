package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        String newStr = "";
        boolean dot = false, exponent = false, minus = false;

        if (str == null || str.isEmpty()) {
            return null;
        }
        if (Character.isDigit(str.charAt(0)) || str.charAt(0) == 'e' || str.charAt(0) == '-') {
            newStr += str.charAt(0);
        }
        if (str.charAt(0) == '-') {
            minus = true;
        }

        for (int i = 1; i < str.length(); i++) {
            if ((!newStr.isEmpty() || minus == true /*|| newStr.charAt(newStr.length()-1)!='e'*/ ) && (str.charAt(i) == '-')  || (str.charAt(i) == '.' && dot == true) || (str.charAt(i) == 'e' && exponent == true))
            {
                return null;
            }

            if (Character.isDigit(str.charAt(i))) {
                newStr += str.charAt(i);
                continue;
            }

            if (str.charAt(i) == '.') {
                newStr += str.charAt(i);
                dot = true;
                continue;
            }

            if (str.charAt(i) == 'e') {
                newStr += str.charAt(i);
                exponent = true;
                continue;
            }

            if (str.charAt(i) == '-') {
                newStr += str.charAt(i);
                minus = true;
            }
        }
        if (dot==true)
        {

            if (exponent==true)
            {
                return parseExponentialNumber(newStr);
            }
            return Double.valueOf(newStr);
        }
        if (((Double.valueOf(newStr).longValue())<Integer.MIN_VALUE) || ((Double.valueOf(newStr).longValue())>Integer.MAX_VALUE))
        {
            if (exponent==true)
            {
                return Double.valueOf(parseExponentialNumber(newStr)).longValue();
            }

            return Double.valueOf(newStr).longValue();
        }
        if (exponent==true)
        {
            return Double.valueOf(parseExponentialNumber(newStr)).intValue();
        }
        return Double.valueOf(newStr).intValue();
    }



    public static double parseExponentialNumber(String input) {

        input = input.toLowerCase();
        int expIndex = input.indexOf("e");
        if (expIndex == -1) {
            return Double.parseDouble(input);
        }
        double base = Double.parseDouble(input.substring(0, expIndex));
        int exponent = Integer.parseInt(input.substring(expIndex + 1));

        return base * Math.pow(10, exponent);
    }
}

