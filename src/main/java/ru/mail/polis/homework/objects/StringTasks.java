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

        if (str.indexOf('.') != str.lastIndexOf('.') || str.indexOf('e') != str.lastIndexOf('e') || str.endsWith("-") || str.endsWith("e")) {
            return null;
        }
        if (str.indexOf('-') >= 0 & str.charAt(str.indexOf('-') + 1) == '-' || str.indexOf('-') >= 0 & str.charAt(str.indexOf('-') + 1) == 'e') {
            return null;
        }

        StringBuilder resulStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '.' || c == 'e' || c == '-' || Character.isDigit(c)) {
                resulStr.append(c);
            }
        }

        if (resulStr.indexOf(".") >= 0 || resulStr.indexOf("e") >= 0){
            return Double.valueOf(resulStr.toString());
        }
        long resultLong = Long.valueOf(resulStr.toString());
        if (resultLong <= 2147483647 && resultLong >= -2147483648) {
            return Integer.valueOf(resulStr.toString());
        }
        return resultLong;
    }
}
