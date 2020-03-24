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
        if ((str == null)||(str.length() == 0)){
            return null;
        }
        Character symbol = null;
        int num = 0;
        int order = 0;
        boolean e = false;
        boolean isNumNegative = false;
        boolean isOrderNegative = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '-') {
                if (e) {
                    if (!isOrderNegative) {
                        isOrderNegative = true;
                    } else {
                        return null;
                    }
                } else {
                    if (!isNumNegative) {
                        isNumNegative = true;
                    } else {
                        return null;
                    }
                }
            }
            if (symbol.isDigit(str.charAt(i))) {
                if (e) {
                    order = order * 10 + (str.charAt(i) - '0');
                } else {
                    num = num * 10 + (str.charAt(i) - '0');
                }
            }
            if (str.charAt(i) == 'e') {
                if (e) {
                    return null;
                } else {
                    e = true;
                }
            }
        }
        if (isNumNegative) {
            num *= -1;
        }
        if (isOrderNegative) {
            order *= -1;
        }
        return Math.pow(num, order);
    }
}
