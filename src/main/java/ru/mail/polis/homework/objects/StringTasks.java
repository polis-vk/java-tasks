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
        if (str == null || str.equals("")) {
            return null;
        }
        int length = str.length();
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            // флаг для проверки - кастить ли к Double (если . или 'e' - ДА)
            if (str.charAt(i) == '.' || str.charAt(i) == 'e') {
                flag = true;
            }
            if (!(Character.isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == '.' || str.charAt(i) == 'e')) {
                str = str.substring(0, i) + str.substring(i + 1);
                i--;
                length--;
            }
        }
        try {
            if (flag) {
                return Double.parseDouble(str);
            } else {
                long result = Long.parseLong(str);
                if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                    return result;
                } else {
                    return (int) result;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
}
