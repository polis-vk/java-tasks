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
        String clear = "";
        int eCount = 0;
        int dotCount = 0;
        for (int i = 0; i < str.length(); i++){
            if ((Character.isDigit(str.charAt(i)) || (str.charAt(i) == '-' ) ||
                (str.charAt(i) == '.' ) || (str.charAt(i) == 'e' ))) {
                    clear += str.charAt(i);
            }
        }
        for (int i = 1; i < clear.length(); i++) {
            if (clear.charAt(i) == 'e') {
                eCount++;
                if (eCount > 1) {
                    return null;
                }
                if ((clear.charAt(i) == 'e') && (i >= clear.length() - 1)) {
                    return null;
                }
            }
            if (clear.charAt(i) == '.') {
                dotCount++;
                if (dotCount > 1) {
                    return null;
                }
            }
            if (((clear.charAt(i) == '-') && (i != 0) && (clear.charAt(i - 1) != 'e'))) {
                return null;
            }
        }
        if ((eCount > 0) || (dotCount > 0)) {
            return Double.parseDouble(clear);
        }
        Long clearL = Long.parseLong(clear);
        if ((clearL > Integer.MAX_VALUE) || (clearL < Integer.MIN_VALUE)){
            return clearL;
        }
        return Integer.parseInt(clear);
    }
}