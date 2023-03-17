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
                continue;
            }
            if (c == '-') {
                if (res.length() == 0 && !isNegative) {
                    res.append(c);
                    isNegative = true;
                    continue;
                } else if (res.charAt(res.length() - 1) == 'e') {
                    res.append(c);
                    continue;
                }
                return null;
            }
            if (c == '.') {
                if (countE < 1 && countDot < 1 && res.length() > 0) {
                    res.append(c);
                    countDot++;
                    continue;
                }
                return null;
            }
            if (c == 'e') {
                if (countE < 1 && res.length() > 0) {
                    res.append(c);
                    countE++;
                    continue;
                }
                return null;
            }
        }
        if (res.charAt(res.length() - 1) == 'e' || res.charAt(res.length() - 1) == '-') { //исключительные случаи когда результативная строка оканчивается на 'e' или '-' например 1.3e или 1.3e-
            return null;
        }
        if (countDot == 0 && countE == 0) {
            long temp = Long.parseLong(res.toString());
            if (temp >= Integer.MIN_VALUE && temp <= Integer.MAX_VALUE) {
                return (int) temp;
            }
            return temp;
        }
        return Double.parseDouble(res.toString());
    }
}
