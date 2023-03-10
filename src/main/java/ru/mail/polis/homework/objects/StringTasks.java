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
        if (str == null || str == "") {
            return null;
        }
        StringBuilder number = new StringBuilder();
        int dotsCnt = 0;
        int expCnt = 0;
        for (int i = 0; i < str.length() - 1; i++) {
            char curSym = str.charAt(i);
            char nextSym = str.charAt(i + 1);
            if (!(curSym == '-' ||  curSym == '.' || curSym == 'e' || Character.isDigit(curSym))) {
                continue;
            }
            if (curSym == '-' && nextSym == '-') {
                return null;
            }
            if (curSym == '.') {
                dotsCnt++;
            }
            if (curSym == 'e') {
                expCnt++;
            }
            number.append(curSym);
        }
        if (str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == 'e') {
            return null;
        }
        if (Character.isDigit(str.charAt(str.length() - 1))) {
            number.append(str.charAt(str.length() - 1));
        }
        if (dotsCnt > 1 || expCnt > 1) {
            return null;
        }
        for (int i = 1; i < number.length() - 1; i++) {
            char prevSym = number.charAt(i - 1);
            char curSym = number.charAt(i);
            char nextSym = number.charAt(i + 1);
            if (curSym == '-' && ( prevSym != 'e' || !Character.isDigit(nextSym) )) {
                return null;
            }
        }
        if (number.indexOf("e") != -1 || number.indexOf(".") != -1) {
            return Double.valueOf(number.toString());
        } else if (Long.valueOf(number.toString()) > Integer.MAX_VALUE
                   || Long.valueOf(number.toString()) < Integer.MIN_VALUE) {
            return Long.valueOf(number.toString());
        }
        return Integer.valueOf(number.toString());
    }
}
