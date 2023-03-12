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
        if (str == null || str.isEmpty()) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        int countDot = 0;
        int countE = 0;
        boolean isNegative = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                res.append(c);
            } else if (c == '-') {
                if (res.length() == 0 && !isNegative) {
                    res.append(c);
                    isNegative = true;
                } else if (res.charAt(res.length() - 1) == 'e') {
                    res.append(c);
                } else {
                    return null;
                }
            } else if (c == '.') {
                if (countE < 1 && countDot < 1 && res.length() > 0) {
                    res.append(c);
                    countDot++;
                } else {
                    return null;
                }
            } else if (c == 'e') {
                if (countE < 1 && res.length() > 0) {
                    res.append(c);
                    countE++;
                } else {
                    return null;
                }
            }
        }
        if (res.charAt(res.length() - 1) == 'e') {
            return null;
        }
        if (countDot == 0 && countE == 0) {
            Long temp = Long.valueOf(res.toString());
            if (temp >= Integer.MIN_VALUE && temp <= Integer.MAX_VALUE) {
                return temp.intValue();
            }
            return temp;
        }
        return Double.valueOf(res.toString());
    }
}
