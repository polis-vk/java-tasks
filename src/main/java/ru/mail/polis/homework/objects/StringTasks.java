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

        String newStr = "";

        int eNum = 0;
        int minusNum = 0;
        int dotNum = 0;

        for (int i = 0; i < str.length(); i++) {
            char workSym = str.charAt(i);
            if (isNumber(workSym) || workSym == 'e' || workSym == '.' || workSym == '-') {
                switch (workSym) {
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
                newStr += workSym;
            }
        }

        int eInd = -1;
        int dotInd = -1;
        int fMinusInd = -1;
        int sMinusInd = -1;

        for (int i = 0; i < newStr.length(); i++) {
            char workSym = newStr.charAt(i);
            switch (workSym) {
                case 'e':
                    eInd = i;
                    break;
                case '.':
                    dotInd = i;
                    break;
                case '-':
                    if (fMinusInd == -1){
                        fMinusInd = i;
                    } else {
                        sMinusInd = i;
                    }
                    break;
            }
        }

        if (!isCorrectPlacement(newStr, eInd, dotInd, fMinusInd, sMinusInd)){
            return null;
        }

        String[] strParts = {null, null, null, null};

        int sign = 1;

        if (newStr.charAt(0) == '-') {
            eInd--;
            dotInd--;
            fMinusInd--;
            sign = -1;
            newStr = newStr.substring(1);
        }



        if (eInd > 0) {
            strParts[0] = newStr.substring(0, eInd);
            strParts[1] = newStr.substring(eInd + 1);
        }

        if (dotInd > 0) {

            if (strParts[0] != null) {
                strParts[2] = strParts[0].substring(0, dotInd);
                strParts[3] = strParts[0].substring(dotInd + 1);
            } else {
                strParts[2] = newStr.substring(0, dotInd);
                strParts[3] = newStr.substring(dotInd + 1);
            }

        }

        if (strParts[3] != null) {

            return sign * (parse(strParts[2], 0) + parse(strParts[3], 1)) * Math.pow(10, parse(strParts[1], 2));

        } else if (strParts[1] != null) {

            return sign * parse(strParts[0], 0) * Math.pow(10, parse(strParts[1], 2));

        } else if (sign * parse(newStr, 0) > Integer.MAX_VALUE || sign * parse(newStr, 0) < Integer.MIN_VALUE) {

            return (long) (sign * parse(newStr, 0));

        }else {

            return (int) (sign * parse(newStr, 0));

        }
    }


    private static boolean isFirstOrLast(String str, int ind) {
        if (ind == 0 || ind ==  str.length() - 1) {
            return true;
        }
        return false;
    }

    private static boolean isNumber(char sym) {
        if (sym >= '0' && sym <= '9' ) {
            return true;
        }
        return false;
    }

    private static boolean isCorrectPlacement(String str, int eInd, int dotInd, int fMinusInd, int sMinusInd) {
        if (isFirstOrLast(str, eInd) || isFirstOrLast(str, dotInd) || isFirstOrLast(str, sMinusInd)) {
            return false;
        }

        if (dotInd != -1) {
            if (!isNumber(str.charAt(dotInd - 1)) || !isNumber(str.charAt(dotInd + 1))) {
                return false;
            }
        }

        if (eInd != -1) {

            if (fMinusInd != -1){
                if (sMinusInd != -1 && sMinusInd != eInd + 1){
                    return false;
                } else if (!isNumber(str.charAt(eInd - 1))) {
                    return false;
                }
                if (fMinusInd != 0) {
                    if (fMinusInd != eInd + 1) {
                        return false;
                    } else if (!isNumber(str.charAt(eInd - 1))) {
                        return false;
                    }

                } else if (sMinusInd == -1 && (!isNumber(str.charAt(eInd - 1)) || !isNumber(str.charAt(eInd + 1)) ) ){
                    return false;
                }
            }
        } else {
            if (fMinusInd != -1) {
                if (fMinusInd != 0 || sMinusInd != -1) {
                    return false;
                }
            }
        }

        return true;
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
