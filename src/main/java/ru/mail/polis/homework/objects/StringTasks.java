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
        if (str == null || str.length() == 0) {
            return null;
        }
        String number = convertStringToNumberForm(str);
        if (number == null) {
            return null;
        }
        return stringToNumber(number);
    }

    private static String convertStringToNumberForm(String str) {
        StringBuilder number = new StringBuilder();
        boolean point = false;
        boolean e = false;
        boolean dash = false;
        boolean firstSymbol = false;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '-':
                    if ((firstSymbol && number.charAt(number.length() - 1) != 'e') || dash) {
                        return null;
                    }
                    dash = true;
                    number.append(str.charAt(i));
                    break;
                case '.':
                    if (point) {
                        return null;
                    }
                    point = true;
                    number.append(str.charAt(i));
                    break;
                case 'e':
                    if (e || !firstSymbol) {
                        return null;
                    }
                    e = true;
                    dash = false;
                    point = true;
                    firstSymbol = false;
                    number.append(str.charAt(i));
                    break;
                default:
                    if (Character.isDigit(str.charAt(i))) {
                        number.append(str.charAt(i));
                        firstSymbol = true;
                    }
                    break;
            }
        }
        if (number.charAt(number.length() - 1) == 'e' || number.charAt(number.length() - 1) == '-') {
            return null;
        }
        return number.toString();
    }

    private static Number stringToNumber(String number) {
        if (number.contains("e") || number.contains(".")) {
            return Double.parseDouble(number);
        }
        long temp = Long.parseLong(number);
        if (temp <= Integer.MAX_VALUE && temp >= Integer.MIN_VALUE) {
            return (int) temp;
        }
        return temp;
    }
}


