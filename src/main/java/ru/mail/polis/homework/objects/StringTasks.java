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

        StringBuilder clearStr = new StringBuilder();

        int eInd = -1;
        int dotInd = -1;
        int fMinusInd = -1;
        int sMinusInd = -1;
        int clearStrInd = 0;

        for (int i = 0; i < str.length(); i++) {
            char currSym = str.charAt(i);
            if (isNumber(currSym) || currSym == 'e' || currSym == '.' || currSym == '-') {
                switch (currSym) {
                    case 'e':
                        if (eInd != -1) {
                            return null;
                        }
                        eInd = clearStrInd;
                        break;
                    case '.':
                        if (dotInd != -1) {
                            return null;
                        }
                        dotInd = clearStrInd;
                        break;
                    case '-':
                        if (sMinusInd != -1) {
                            return null;
                        }
                        if (fMinusInd == -1){
                            fMinusInd = clearStrInd;
                        } else {
                            sMinusInd = clearStrInd;
                        }
                        break;
                }
                clearStr.append(currSym);
                clearStrInd++;
            }
        }

        if (!isCorrectPlacement(clearStr, eInd, dotInd, fMinusInd, sMinusInd)){
            return null;
        }

        int sign1 = 1;
        int sign2 = 1;

        if (sMinusInd != -1 || (fMinusInd != -1 && fMinusInd != 0)) {
            sign2 = -1;
        }
        if (fMinusInd == 0) {
            sign1 = -1;
        }


        long integer = 0;
        double fraction = 0;
        int exp = 0;
        int fractionCounter = 0;

        for (int i = 0; i < clearStr.length(); i++) {
            char currSym = clearStr.charAt(i);

            if (isNumber(currSym)) {

                if (eInd != -1) {
                    if (i < eInd) {

                        if (dotInd != -1) {
                            if (i < dotInd) {
                                integer *= 10;
                                integer += currSym - 48;
                            }
                            if (i > dotInd) {
                                fractionCounter--;
                                fraction *= 10;
                                fraction += currSym - 48;
                            }

                        } else {
                            integer *= 10;
                            integer += currSym - 48;
                        }
                    }
                    if (i > eInd) {
                        exp *= 10;
                        exp += currSym - 48;
                    }
                } else if (dotInd != -1) {
                    if (i < dotInd) {
                        integer *= 10;
                        integer += currSym - 48;
                    }
                    if (i > dotInd) {
                        fractionCounter--;
                        fraction *= 10;
                        fraction += currSym - 48;
                    }
                } else {
                    integer *= 10;
                    integer += currSym - 48;
                }
            }

        }

        if (eInd == -1 && dotInd == -1) {
            long result = sign1 * integer;
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return result;
            }
                return (int) result;
        } else {
            return sign1 * (integer + fraction * Math.pow(10, fractionCounter)) * Math.pow(10, sign2 * exp);
        }

    }

    private static boolean isFirstOrLast(StringBuilder str, int ind) {
        return ind == 0 || ind == str.length() - 1;
    }

    private static boolean isNumber(char sym) {
        return sym >= '0' && sym <= '9';
    }

    private static boolean isCorrectPlacement(StringBuilder str, int eInd, int dotInd, int fMinusInd, int sMinusInd) {
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
                    } else return isNumber(str.charAt(eInd - 1));

                } else return sMinusInd != -1 || (isNumber(str.charAt(eInd - 1)) && isNumber(str.charAt(eInd + 1)));
            }
        } else {
            if (fMinusInd != -1) {
                return fMinusInd == 0 && sMinusInd == -1;
            }
        }

        return true;
    }

}
