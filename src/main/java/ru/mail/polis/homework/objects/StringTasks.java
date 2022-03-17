package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.equals("") || str.indexOf('.') != str.lastIndexOf('.') || str.indexOf('e') != str.lastIndexOf('e')) {
            return null;
        }
        StringBuilder newStr = new StringBuilder();
        int indexE = -1;
        int indexPoint = -1;
        int lenght = 0;
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (Character.isDigit(current)) {
                newStr.append(current);
                lenght++;
                }
            else {
                switch (current) {
                    case '-':
                        newStr.append(current);
                        int indexMinus = lenght;
                        if (indexE + 1 != indexMinus) {
                            return null;
                        }
                        lenght++;
                        break;
                    case 'e':
                        newStr.append(current);
                        indexE = lenght;
                        lenght++;
                        break;
                    case '.':
                        newStr.append(current);
                        indexPoint = lenght;
                        if (indexE != -1 && indexPoint > indexE) {
                            return null;
                        }
                        lenght++;
                        break;
                }
            }
        }
        String numberString = newStr.toString();
        if (indexE == lenght - 1){
                return null;
            }
        if (indexE != -1 || indexPoint != -1) {
            return toDouble(numberString);
        }
        long l = toLong(numberString);

        if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
            return ((int) l);
        }
        return l;
    }

    private static long toLong(String str) {
        long number = 0;
        int i = 0;
        if (str.charAt(0) == '-') {
            i = 1;
        }
        for (; i < str.length(); i++) {
            number = number * 10 + (int) str.charAt(i) - 48;
        }
        if (str.charAt(0) == '-') {
            number = -number;
        }
        return number;
    }

    private static double toDouble(String str) {
        double number = 0;
        int i;
        for (i = 0; i < str.length() && str.charAt(i) != 'e' ; i++) {
            if (Character.isDigit(str.charAt(i))) {
                number = number * 10 + (int) str.charAt(i) - 48;
            }
        }
        if (str.charAt(0) == '-') {
            number = -number;
        }
        if (str.contains(".")) {
            int j = str.indexOf('.');
            while (j < str.length() && str.charAt(j) != 'e') {
                j++;
            }
            j = j - str.indexOf('.') - 1;
            number = number / Math.pow(10, j);
        }
        if (i < str.length() && str.charAt(i) == 'e') {
            int pow = 0;
            for (int k = i + 1; k < str.length(); k++) {
                if (Character.isDigit(str.charAt(k))) {
                    pow = pow * 10 + (int) str.charAt(k) - 48;
                }
            }
            if (str.charAt(i + 1) == '-') {
                number = number / Math.pow(10, pow);
            } else {
                number = number * Math.pow(10, pow);
            }
        }
        return number;
    }
}
