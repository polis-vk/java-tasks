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
        if (str == null || str.equals("")) {
            return null;
        }

        String clearStr = "";

        int eNum = 0;
        int minusNum = 0;
        int dotNum = 0;

        for (int i = 0; i < str.length(); i++) {
            char currSym = str.charAt(i);
            if (currSym >= '0' && currSym <= '9' || currSym == 'e' || currSym == '.' || currSym == '-') {
                switch (currSym) {
                    case 'e':
                        eNum++;
                        break;
                    case '-':
                        minusNum++;
                        break;
                    case '.':
                        dotNum++;
                        break;
                }
                if (eNum > 1 || dotNum > 1 || minusNum > 2) {
                    return null;
                }
                clearStr += currSym;
            }
        }

        char firstSym = clearStr.charAt(0);
        int strLen = clearStr.length();
        char lastSym = clearStr.charAt(clearStr.length() - 1);

        // Exceptions

        if (firstSym == '0' && strLen > 1 ||
                firstSym == '.' || lastSym == '.' ||
                firstSym == 'e' || lastSym == 'e' ||
                lastSym == '-') {
            return null;
        }

        String beforeE = null;
        String afterE = null;
        String beforeDot = null;
        String afterDot = null;


        int sign = 1;

        if (clearStr.charAt(0) == '-') {
            sign = -1;
            clearStr = clearStr.substring(1);
            if (minusNum == 2) {
                if (clearStr.charAt(0) == '-' || clearStr.charAt(clearStr.indexOf('-') - 1) != 'e') {
                    return null;
                }
            }
        } else if (minusNum == 1 && clearStr.charAt(clearStr.indexOf('-') - 1) != 'e' || minusNum == 2) {
            return null;
        }


        int indexOfE = clearStr.indexOf('e');


        if (indexOfE != -1) {
            if (
                    clearStr.charAt(indexOfE - 1) < '0' || clearStr.charAt(indexOfE - 1) > '9' ||
                            (clearStr.charAt(indexOfE + 1) < '0' || clearStr.charAt(indexOfE + 1) > '9') &&
                                    clearStr.charAt(indexOfE + 1) != '-') {
                return null;
            }

            beforeE = clearStr.substring(0, indexOfE);
            afterE = clearStr.substring(indexOfE + 1);

        } else if (minusNum == 2 || clearStr.indexOf('-') != -1) {
            return null;
        }

        int indexOfDot = clearStr.indexOf('.');


        if (indexOfDot != -1) {
            if (
                    clearStr.charAt(indexOfDot - 1) < '0' || clearStr.charAt(indexOfDot - 1) > '9' ||
                            clearStr.charAt(indexOfDot + 1) < '0' || clearStr.charAt(indexOfDot + 1) > '9') {
                return null;
            }

            if (indexOfE != -1) {
                beforeDot = beforeE.substring(0, indexOfDot);
                afterDot = beforeE.substring(indexOfDot + 1);
            } else {
                beforeDot = clearStr.substring(0, indexOfDot);
                afterDot = clearStr.substring(indexOfDot + 1);
            }

        }

        if (afterDot != null) {

            return sign * (StringTasks.parse(beforeDot, 0) + StringTasks.parse(afterDot, 1)) * Math.pow(10, StringTasks.parse(afterE, 2));

        } else if (afterE != null) {

            return sign * StringTasks.parse(beforeE, 0) * Math.pow(10, StringTasks.parse(afterE, 2));

        } else if (sign * StringTasks.parse(clearStr, 0) > Integer.MAX_VALUE || sign * StringTasks.parse(clearStr, 0) < Integer.MIN_VALUE) {

            return (long) (sign * StringTasks.parse(clearStr, 0));

        }else {

            return (int) (sign * StringTasks.parse(clearStr, 0));

        }
    }

    private static double parse(String str, int situation) {
        if (str == null){
            return 0;
        }
        double number = 0;
        int len = str.length();

        switch (situation){
            case 0:
                for (int i = 0; i < len; i++) {
                    number += (str.charAt(i) - 48) * Math.pow(10, len - i - 1);
                }
                break;
            case 1:
                for (int i = 0; i < len; i++) {
                    number += (str.charAt(i) - 48) * Math.pow(10, -1 - i );
                }
                break;
            case 2:
                int sign = 1;
                if (str.charAt(0) == '-'){
                    sign = -1;
                    str = str.substring(1);
                    len--;
                }
                for (int i = 0; i < len; i++) {
                    number += (str.charAt(i) - 48) * Math.pow(10, len - i - 1);
                }
                number *= sign;
                break;
        }

        return number;
    }
}
