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
        if (str == null || str.isEmpty()) {
            return null;
        }
        StringBuilder number = new StringBuilder();
        boolean flagPoint = true;
        boolean flagE = true;
        boolean flagDash = true;
        boolean firstSymbol = true;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                number.append(str.charAt(i));
                firstSymbol = false;
            } else {
                switch (str.charAt(i)) {
                    case '-':
                        if (flagDash && flagPoint && firstSymbol) {
                            flagDash = false;
                            number.append(str.charAt(i));
                        } else {
                            return null;
                        }
                        break;
                    case '.':
                        if (flagPoint) {
                            flagPoint = false;
                            number.append(str.charAt(i));
                        } else {
                            return null;
                        }
                        break;
                    case 'e':
                        if (flagE) {
                            flagE = false;
                            flagDash = true;
                            flagPoint = true;
                            firstSymbol = true;
                            number.append(str.charAt(i));
                        } else {
                            return null;
                        }
                        break;
                }
            }
        }
        if(number.charAt(number.length() - 1) == 'e'){
            return null;
        }
        if (!flagPoint || !flagE) {
            return Double.parseDouble(String.valueOf(number));
        }
        if (Long.parseLong(String.valueOf(number)) <= Integer.MAX_VALUE && Long.parseLong(String.valueOf(number)) >= Integer.MIN_VALUE) {
            return Integer.parseInt(String.valueOf(number));
        }
        return Long.parseLong(String.valueOf(number));
    }
}
